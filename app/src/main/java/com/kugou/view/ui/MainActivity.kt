package com.kugou.view.ui

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.blankj.rxbus.RxBus
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.commom.framwork.FrameworkActivity
import com.kugou.player.manager.Action
import com.kugou.player.manager.PlayAction
import com.kugou.player.PlayerBackUtils
import com.kugou.utils.KgUtils
import com.kugou.utils.dp2px
import com.kugou.view.ui.dialog.PlayListDialog
import com.kugou.view.ui.fragment.netmusic.NetMusicFragment
import com.kugou.view.widget.KGSeekBar
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_playbar.*
import kotlinx.android.synthetic.main.playbar_right.*

class MainActivity : FrameworkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.main_container, NetMusicFragment())
                .commit()

        val layoutTransition = LayoutTransition()
        layoutTransition.setStagger(LayoutTransition.APPEARING, 300)
        layoutTransition.setStagger(LayoutTransition.DISAPPEARING, 200)
        val enter = ObjectAnimator.ofFloat(null, "translationY", dp2px(70f) * 1f, 0f)
        layoutTransition.setAnimator(LayoutTransition.APPEARING, enter)
        val exit = ObjectAnimator.ofFloat(null, "translationY", 0f, dp2px(70f) * 1f)
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, exit)
        layoutTransition.setDuration(300)
        play_bar_container.layoutTransition = layoutTransition
        playing_bar.setOnClickListener {
            startActivity(Intent(this@MainActivity, PlayActivity::class.java))
        }

        sb_play.isPreventTapping = true
        sb_play.setOnSeekBarChangeListener(object : KGSeekBar.AbsOnSeekBarChangeListener() {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                PlayerBackUtils.seek(progress)
            }
        })

        btn_playin_bar_next.setOnClickListener {
            PlayerBackUtils.next()
        }

        btn_playing_bar_play.setOnClickListener {
            PlayerBackUtils.play()
        }
        updateBar()
        RxBus.getDefault().subscribe(this, AndroidSchedulers.mainThread(), object : RxBus.Callback<Action> {
            override fun onEvent(action: Action) {
                when (action.action) {
                    PlayAction.PLAY_ACTION_PROGRESS -> {
                        if (!sb_play.isPressed) {
                            sb_play.progress = (action.data as Long / 1000).toInt()
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
                        updateBar()
                    }
                }

            }
        })

        btn_playing_bar_playlist.setOnClickListener {

            playList.show(supportFragmentManager, "playlist")
        }
    }

    val playList = PlayListDialog()

    fun updateState() {
        val res: Int
        if (PlayerBackUtils.isPaying()) {
            res = R.drawable.playing_bar_pause
            img_rote_singer.start()
        } else {
            res = R.drawable.playing_bar_play
            img_rote_singer.stop()
        }

        btn_playing_bar_play.setImageResource(res)
    }

    private fun updateBar() {
        val music = PlayerBackUtils.playMusic()
        val info = KgUtils.bar(music?.filename)
        tv_playing_bar_song_name.text = info.first
        tv_playin_bar_singer.text = info.second
        sb_play.max = music?.duration?.toInt() ?: 100
        sb_play.progress = 0
        CommBindingAdapter.setPlaceholder(img_rote_singer, music?.topic_url, R.drawable.ico_playing_bar_default_avatar)
    }

    override fun onBackPressed() {
        if (!fragmentStackManager.onBackPress()) {
            moveTaskToBack(true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (playing_bar.isGone)
            handler.postDelayed({
                playing_bar.isVisible = true
            }, 1)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        playing_bar.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }
}