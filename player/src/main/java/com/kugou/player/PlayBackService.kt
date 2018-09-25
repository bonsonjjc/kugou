package com.kugou.player

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.view.SurfaceHolder
import com.blankj.rxbus.RxBus
import com.danikula.videocache.HttpProxyCacheServer
import com.kugou.player.manager.Action
import com.kugou.player.manager.PlayAction
import com.kugou.player.manager.QueueManager
import com.kugou.audio.model.Music
import com.kugou.player.model.UrlService
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class PlayBackService : Service() {
    var playBackUtils: PlayBackBinder = PlayBackBinder()

    lateinit var proxyServer: HttpProxyCacheServer
    val mediaPlayer = IjkMediaPlayer()
    private val queueManager = QueueManager()
    val playBack get() = queueManager.playBack

    override fun onBind(intent: Intent?): IBinder {
        return playBackUtils
    }

    override fun onCreate() {
        super.onCreate()
        proxyServer = HttpProxyCacheServer(applicationContext)
    }

    val bus = RxBus.getDefault()

    val progressHandler: ProgressHandler = ProgressHandler()

    @SuppressLint("HandlerLeak")
    inner class ProgressHandler : Handler() {
        var isStart = false

        fun start(time: Long = 0L) {
            if (isStart.not()) {
                send(time)
            }
            isStart = true
        }

        fun stop() {
            removeCallbacksAndMessages(null)
            isStart = false
        }

        var count = 0

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (mediaPlayer.isPlaying) {
                bus.post(Action(PlayAction.PLAY_ACTION_PROGRESS, mediaPlayer.currentPosition))
                count = 0
                val duration = mediaPlayer.currentPosition % 1000
                send(998 - duration)
            } else {
                count++
                if (count < 50) {
                    send(100)
                }
            }
        }

        fun send(time: Long) {
            sendEmptyMessageDelayed(1, time)
        }
    }

    val urlService = UrlService()

    inner class PlayBackBinder : Binder() {

        fun next() {
            queueManager.next()
            bus.post(Action(PlayAction.PLAY_ACTION_NEXT))
            play(true)
        }

        fun previous() {
            queueManager.previous()
            bus.post(Action(PlayAction.PLAY_ACTION_PREVIOUS))
            play(true)
        }

        fun play(index: Int) {
            queueManager.music(index)
            play(true)
        }

        fun play(index: Int = 0, list: List<Music>) {
            if (playBack.playMusic?.hash == list[index].hash) {
                star()
            } else {
                queueManager.add(list, isClear = true)
                queueManager.music(index)
                play(true)
            }
        }

        var isBuffer: Boolean = false

        private fun play(isProxy: Boolean = false) {
            val playMusic = playBack.playMusic
            bus.post(Action(PlayAction.PLAY_ACTION_MUSIC, playMusic))
            mediaPlayer.reset()
            urlService.source(playMusic!!)
                    .subscribe({
                        val proxyUlr = if (isProxy) proxyServer.getProxyUrl(it.url!!.first()) else it.url!!.first()
                        mediaPlayer.setDataSource(proxyUlr)
                        mediaPlayer.prepareAsync()
                        listener()
                    }, {

                    })
        }

        private fun listener() {
            mediaPlayer.setOnInfoListener { _, what, exotr ->
                Log.e("OnInfo", "what:$what ,exotr:$exotr")
                when (what) {
                    IjkMediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                        bus.post(Action(PlayAction.PLAY_ACTION_BUFFER_START))
                        isBuffer = true
                    }
                    IjkMediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                        bus.post(Action(PlayAction.PLAY_ACTION_BUFFER_END))
                        isBuffer = false
                    }
                    IjkMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START, IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                        bus.post(Action(PlayAction.PLAY_ACTION_PLAY))
                        progressHandler.start()
                    }
                }
                true
            }

            mediaPlayer.setOnBufferingUpdateListener { _, percent ->
                Log.e("OnBuffer", "percent:$percent")
                bus.post(Action(PlayAction.PLAY_ACTION_BUFFER_PERCENT, percent))
            }

            mediaPlayer.setOnErrorListener { _, what, exotr ->
                Log.e("OnError", "what:$what ,exotr:$exotr")
                when (what) {
                    IjkMediaPlayer.MEDIA_ERROR_IO -> {

                    }
                    IjkMediaPlayer.MEDIA_ERROR_TIMED_OUT -> {

                    }
                    IjkMediaPlayer.MEDIA_ERROR_UNKNOWN -> {

                    }
                    IjkMediaPlayer.MEDIA_ERROR_UNSUPPORTED -> {

                    }
                }
                next()
                true
            }

            mediaPlayer.setOnPreparedListener {
                bus.post(Action(PlayAction.PLAY_ACTION_PREPARED))
                mediaPlayer.start()
            }

            mediaPlayer.setOnCompletionListener {
                bus.post(Action(PlayAction.PLAY_ACTION_COMPLETION))
                progressHandler.stop()
                next()
            }

        }

        fun star() {
            if (isPlaying()) {
                mediaPlayer.pause()
                bus.post(Action(PlayAction.PLAY_ACTION_PAUSE))
                progressHandler.stop()
            } else {
                mediaPlayer.start()
                progressHandler.start()
                bus.post(Action(PlayAction.PLAY_ACTION_PLAY))
            }
        }

        fun setDisplay(holder: SurfaceHolder?) {
            mediaPlayer.setDisplay(holder)
        }

        fun isPlaying(): Boolean {
            return mediaPlayer.isPlaying
        }

        fun release() {
            mediaPlayer.release()
        }

        fun stop() {
            mediaPlayer.stop()
        }

        fun seek(position: Int) {
            mediaPlayer.seekTo(position * 1000L)
        }

        fun queueManager(): QueueManager {
            return queueManager
        }
    }
}