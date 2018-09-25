package com.kugou.view.ui.fragment.netmusic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.view.ui.adapter.PagerAdapter
import com.kugou.view.ui.fragment.netmusic.rank.RankMainFragment
import com.kugou.view.ui.fragment.netmusic.recommend.RecommendFragment
import com.kugou.view.ui.fragment.netmusic.singer.heat.SingerHeatFragment
import com.kugou.view.ui.fragment.netmusic.special.SpecialMainFragment
import com.kugou.view.ui.fragment.search.SearchFragment

class NetMusicFragment : FrameworkFragment() {
    lateinit var pagerAdapter: PagerAdapter

    override val layoutId: Int get() = R.layout.fragment_netmusic

    lateinit var viewpager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragments = mutableListOf<Fragment>()
        fragments.add(RecommendFragment())
        fragments.add(RankMainFragment())
        fragments.add(SingerHeatFragment())
        fragments.add(SpecialMainFragment())
        val tabItems = mutableListOf("推荐", "排行", "歌手", "分类")
        pagerAdapter = PagerAdapter(tabItems, fragments, childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.toolbar).setBackgroundResource(R.color.theme)
        view.findViewById<TextView>(R.id.tv_title).text = "乐库"
        viewpager = view.findViewById(R.id.viewpager)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewpager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewpager)

        view.findViewById<View>(R.id.btn_right)
                .setOnClickListener {
                    childFragmentManager.beginTransaction()
                            .add(R.id.container, SearchFragment())
                            .addToBackStack("search")
                            .commit()
                }
    }
}