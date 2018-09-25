package com.kugou.view.ui.fragment.netmusic.special

import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.special.Special
import com.kugou.utils.KgUtils
import com.kugou.view.ui.adapter.PagerAdapter
import com.kugou.view.ui.comm.ScrollFragment
import com.kugou.view.ui.fragment.netmusic.singer.songs.SongFragment
import com.kugou.view.ui.fragment.netmusic.special.similar.SimilarSpecialFragment

class SpecialFragment : ScrollFragment() {
    override val headLayoutId: Int = R.layout.layout_special_detail_header
    override val contentLayoutId: Int = R.layout.layout_viewpager
    override val toolbarLayoutId: Int = R.layout.layout_toolbar

    lateinit var pagerAdapter: PagerAdapter

    val special: Special by lazy { arguments!!.getParcelable<Special>("data") }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragments = mutableListOf<androidx.fragment.app.Fragment>()
        fragments.add(SongFragment.special(special.specialid))
        fragments.add(SimilarSpecialFragment())
        val titles = mutableListOf("歌曲列表", "相似歌单")
        pagerAdapter = PagerAdapter(titles, fragments, childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<androidx.viewpager.widget.ViewPager>(R.id.viewpager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)


        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = special.specialname

        val tvCreateTime = view.findViewById<TextView>(R.id.tv_create_time)
        tvCreateTime.text = "创建时间 ${KgUtils.date(special.publishtime)}"

        val tvAlbumName = view.findViewById<TextView>(R.id.tv_creater)
        tvAlbumName.text = special.username

        val imageView = view.findViewById<ImageView>(R.id.img_cover)
        CommBindingAdapter.setUrl(imageView, special.imgurl)

        view.findViewById<View>(R.id.btn_left).setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance(bean: Special): SpecialFragment {
            val bundle = Bundle()
            bundle.putParcelable("data", bean)
            val fragment = SpecialFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}