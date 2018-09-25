package com.kugou.view.ui.fragment.netmusic.singer

import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.View
import android.widget.TextView
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.singer.Singer
import com.kugou.utils.KgUtils
import com.kugou.view.ui.adapter.PagerAdapter
import com.kugou.view.ui.comm.ScrollFragment
import com.kugou.view.ui.fragment.netmusic.album.AlbumsFragment
import com.kugou.view.ui.fragment.mv.MvListFragment
import com.kugou.view.ui.fragment.netmusic.singer.detail.DetailFragment
import com.kugou.view.ui.fragment.netmusic.singer.songs.SongFragment

class SingerFragment : ScrollFragment() {
    lateinit var pagerAdapter: PagerAdapter
    val singer: Singer by lazy { arguments!!.getParcelable<Singer>("data") }
    override val headLayoutId: Int = R.layout.layout_singer_header
    override val contentLayoutId: Int = R.layout.layout_viewpager
    override val toolbarLayoutId: Int = R.layout.layout_toolbar

    lateinit var tableLayout: com.google.android.material.tabs.TabLayout
    lateinit var viewpager: androidx.viewpager.widget.ViewPager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragments = mutableListOf<androidx.fragment.app.Fragment>()
        fragments.add(SongFragment.newInstance(singer.singerid))
        fragments.add(AlbumsFragment.newInstance(singer.singerid))
        fragments.add(MvListFragment.newInstance(singer.singerid))
        fragments.add(DetailFragment.newInstance(singer.singerid))
        val titles = mutableListOf("单曲", "专辑", "MV", "详情")
        pagerAdapter = PagerAdapter(titles, fragments, childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CommBindingAdapter.setUrl(view.findViewById(R.id.imgCover), singer.imgurl)
        view.findViewById<TextView>(R.id.tv_title).text = singer.singername
        view.findViewById<TextView>(R.id.tv_singer_name).text = singer.singername
        view.findViewById<TextView>(R.id.tv_fans).text = "粉丝:" + KgUtils.numberTo(singer.fanscount) + "  |"
        view.findViewById<View>(R.id.btn_left).setOnClickListener { activity?.onBackPressed() }

        tableLayout = view.findViewById(R.id.tabLayout)
        viewpager = view.findViewById(R.id.viewpager)

        tableLayout.setupWithViewPager(viewpager, true)
        viewpager.adapter = pagerAdapter
    }

    companion object {
        fun newInstance(singer: Singer): SingerFragment {
            val bundle = Bundle()
            bundle.putParcelable("data", singer)
            val fragment = SingerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}