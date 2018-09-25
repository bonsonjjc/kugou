package com.kugou.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import com.blankj.rxbus.RxBus
import com.kugou.audio.model.Music
import com.kugou.player.manager.Action
import com.kugou.player.manager.PlayAction
import com.kugou.player.manager.PlayBack

object PlayerBackUtils {
    private var playBackUtils: PlayBackService.PlayBackBinder? = null
    lateinit var context: Context

    fun connect(context: Context) {
        this.context = context
        val serverIntent = Intent(context, PlayBackService::class.java)
        context.startService(serverIntent)
        context.bindService(serverIntent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                playBackUtils = service as PlayBackService.PlayBackBinder
            }

            override fun onServiceDisconnected(name: ComponentName) {
                playBackUtils = null
            }
        }, Context.BIND_AUTO_CREATE)
    }

    fun playBackServer(): PlayBackService.PlayBackBinder {
        return playBackUtils!!
    }

    fun isAlive(): Boolean {
        return playBackUtils == null
    }

    fun seek(position: Int) {
        playBackUtils?.seek(position)
    }

    fun previous() {
        playBackUtils?.previous()
    }

    fun next() {
        playBackUtils?.next()
    }

    fun play() {
        playBackUtils?.star()
    }

    fun add(index: Int = -1, list: List<Music>) {
        val start = if (index == -1) playBackUtils!!.queueManager().count() else index
        playBackUtils?.queueManager()?.add(list, start)
        Toast.makeText(context, "已添加${list.size}首歌到播放列表", Toast.LENGTH_SHORT).show()
        RxBus.getDefault().post(Action(PlayAction.PLAY_ACTION_LIST_CHANGE))
    }

    fun play(index: Int, list: List<Music>) {
        playBackUtils?.play(index, list)
    }

    fun playMusic(): Music? {
        return playBack().playMusic
    }

    fun playBack(): PlayBack {
        return playBackUtils!!.queueManager().playBack
    }

    fun playList(): List<Music>? {
        return playBackUtils?.queueManager()?.list()
    }

    fun isPaying(): Boolean {
        return playBackUtils?.isPlaying() ?: false
    }
}
