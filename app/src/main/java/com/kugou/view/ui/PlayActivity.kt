package com.kugou.view.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.SeekBar
import androidx.core.view.isGone
import com.blankj.rxbus.RxBus
import com.kugou.R
import com.kugou.audio.model.Music
import com.kugou.commom.framwork.FrameworkActivity
import com.kugou.player.manager.Action
import com.kugou.player.manager.PlayAction
import com.kugou.player.PlayerBackUtils
import com.kugou.utils.KgUtils
import com.kugou.view.ui.dialog.PlayListDialog
import com.kugou.view.widget.KGSeekBar
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_player.*

class PlayActivity : FrameworkActivity() {
    val playList = PlayListDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        update(PlayerBackUtils.playMusic())
        updateState()
        RxBus.getDefault().subscribe(this, AndroidSchedulers.mainThread(), object : RxBus.Callback<Action> {
            override fun onEvent(action: Action) {
                when (action.action) {
                    PlayAction.PLAY_ACTION_PROGRESS -> {
                        if (!sb_play.isPressed) {
                            sb_play.progress = (action.data as Long / 1000).toInt()
                            tv_curr_time.text = KgUtils.toMinute(sb_play.progress)
                        }
                    }
                    PlayAction.PLAY_ACTION_PAUSE, PlayAction.PLAY_ACTION_BUFFER_START,
                    PlayAction.PLAY_ACTION_PLAY, PlayAction.PLAY_ACTION_BUFFER_END -> {
                        updateState()
                    }
                    PlayAction.PLAY_ACTION_BUFFER_PERCENT -> {
                        sb_play.secondaryProgress = action.data as Int
                    }
                    PlayAction.PLAY_ACTION_MUSIC -> {
                        update(action.data as Music)
                    }
                }
            }
        })
        sb_play.setOnSeekBarChangeListener(object : KGSeekBar.AbsOnSeekBarChangeListener() {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                PlayerBackUtils.seek(progress)
            }
        })

        btn_play_pre.setOnClickListener {
            PlayerBackUtils.previous()
        }

        btn_play_next.setOnClickListener {
            PlayerBackUtils.next()
        }

        btn_play.setOnClickListener {
            PlayerBackUtils.play()
        }

        btn_left.setOnClickListener {
            onBackPressed()
        }

        btn_play_queue.setOnClickListener {

            playList.show(supportFragmentManager, "playlist")
        }
    }

    fun updateState() {
        val res: Int
        if (PlayerBackUtils.isPaying()) {
            res = R.drawable.ico_main_pause_default
            img_rote_singer.start()
        } else {
            res = R.drawable.btn_main_play
            img_rote_singer.stop()
        }
        btn_play.setImageResource(res)
    }

    fun update(music: Music?) {
        val info = KgUtils.bar(music?.filename)
        tv_title.text = info.first
        tv_singers.text = info.second
        tv_mv.isGone = TextUtils.isEmpty(music?.mvhash)
        sb_play.max = music?.duration?.toInt() ?: 100
        sb_play.progress = 0
        tv_curr_time.text = KgUtils.toMinute(0)
        tv_total_duration.text = KgUtils.toMinute(music?.duration?.toInt() ?: 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }
}