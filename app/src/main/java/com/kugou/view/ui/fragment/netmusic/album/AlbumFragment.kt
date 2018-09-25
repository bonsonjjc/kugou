package com.kugou.view.ui.fragment.netmusic.album

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
import com.kugou.repository.netmusic.album.Album
import com.kugou.utils.KgUtils
import com.kugou.view.ui.adapter.PagerAdapter
import com.kugou.view.ui.comm.ScrollFragment
import com.kugou.view.ui.fragment.netmusic.singer.songs.SongFragment

class AlbumFragment : ScrollFragment() {
    override val headLayoutId: Int = R.layout.layout_album_detail_header
    override val contentLayoutId: Int = R.layout.layout_viewpager
    override val toolbarLayoutId: Int = R.layout.layout_toolbar
    lateinit var pagerAdapter: PagerAdapter

    val album: Album by lazy { arguments!!.getParcelable<Album>("album") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragments = mutableListOf<androidx.fragment.app.Fragment>()
        fragments.add(SongFragment.album(album.albumid))
        fragments.add(AlbumDetailFragment.newInstance(album.albumid))
        val titles = mutableListOf("歌曲列表", "专辑信息")
        pagerAdapter = PagerAdapter(titles, fragments, childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<androidx.viewpager.widget.ViewPager>(R.id.viewpager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = album.albumname

        val tvCreateTime = view.findViewById<TextView>(R.id.tv_create_time)
        tvCreateTime.text = "发行时间 ${KgUtils.date(album.publishtime)}"

        val tvAlbumName = view.findViewById<TextView>(R.id.tv_creater)
        tvAlbumName.text = "歌手 ${album.singername}"

        val imageView = view.findViewById<ImageView>(R.id.img_cover)
        CommBindingAdapter.setUrl(imageView, album.imgurl)

        view.findViewById<View>(R.id.btn_left).setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance(albumId: Album): AlbumFragment {
            val bundle = Bundle()
            bundle.putParcelable("album", albumId)
            val fragment = AlbumFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}