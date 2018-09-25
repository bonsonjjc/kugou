package com.kugou.view.ui.fragment.mv.play

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kugou.R
import com.kugou.commom.framwork.FrameworkActivity
import com.kugou.view.ui.adapter.PagerAdapter
import com.kugou.view.ui.fragment.chat.ChatFragment
import com.kugou.view.ui.fragment.netmusic.mv.similar.SimilarMvFragment
import kotlinx.android.synthetic.main.activity_live_player.*

class MvPlayActivity : FrameworkActivity() {
    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_player)

        val fragments = mutableListOf<androidx.fragment.app.Fragment>()
        fragments.add(SimilarMvFragment())
        fragments.add(ChatFragment())
        val titles = mutableListOf("相关推荐", "评论/401")
        pagerAdapter = PagerAdapter(titles, fragments, supportFragmentManager)

        tabLayout.setupWithViewPager(viewpager)
        viewpager.adapter = pagerAdapter
    }
}