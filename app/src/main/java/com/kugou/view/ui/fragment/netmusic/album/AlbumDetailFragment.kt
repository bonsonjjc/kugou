package com.kugou.view.ui.fragment.netmusic.album

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.utils.KgUtils
import kotlinx.android.synthetic.main.fragment_album_detail.*

class AlbumDetailFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.fragment_album_detail

    lateinit var viewModel: AlbumDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.request(arguments!!.getInt("id"))
        viewModel.dataList.observe(this, Observer {
            tv_album_name.text = it!!.albumname
            tv_album_publish.text = KgUtils.date(it.publishtime)
            tv_album_intro.text = it.intro
        })
    }

    companion object {
        fun newInstance(albumId: Int): AlbumDetailFragment {
            val bundle = Bundle()
            bundle.putInt("id", albumId)
            val fragment = AlbumDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}