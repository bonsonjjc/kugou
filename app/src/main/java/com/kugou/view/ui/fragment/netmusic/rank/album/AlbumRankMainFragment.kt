package com.kugou.view.ui.fragment.netmusic.rank.album

import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.repository.netmusic.rank.RankTag
import com.kugou.view.ui.adapter.PagerAdapter
import com.kugou.view.ui.fragment.netmusic.rank.RankFragment

class AlbumRankMainFragment : RankFragment() {
    lateinit var pagerAdapter: PagerAdapter
    lateinit var viewModel: AlbumRanklViewModel

    override val contentLayoutId: Int = R.layout.layout_viewpager

    lateinit var viewpager: androidx.viewpager.widget.ViewPager
    lateinit var tabLayout: com.google.android.material.tabs.TabLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.init(rankTag)
        val fragments = mutableListOf<androidx.fragment.app.Fragment>()
        fragments.add(AlbumRankFragment.newInstance("oneDay"))
        fragments.add(AlbumRankFragment.newInstance("sevenDays"))
        fragments.add(AlbumRankFragment.newInstance("all"))
        val titles = mutableListOf("今日排行", "本周排行", "累计排行")
        pagerAdapter = PagerAdapter(titles, fragments, childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager = view.findViewById(R.id.viewpager)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewpager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewpager)
    }

    companion object {
        fun newInstance(rankTag: RankTag): AlbumRankMainFragment {
            val bundle = Bundle()
            bundle.putParcelable("tag", rankTag)
            val fragment = AlbumRankMainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}