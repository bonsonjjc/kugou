package com.kugou.view.ui.fragment.netmusic.rank.album

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.repository.netmusic.rank.RankAlbum
import com.kugou.view.ui.adapter.OnItemClickListener

class AlbumRankFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.layout_list

    lateinit var adapter: AlbumAdapter
    lateinit var viewModel: AlbumRanklViewModel

    val type: String by lazy { arguments!!.getString("type") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        adapter = AlbumAdapter(context)
        when (type) {
            "oneDay" -> {
                viewModel.songs.observe(this, Observer<List<RankAlbum>> { t ->
                    adapter.dataList.addAll(t!!)
                    adapter.notifyDataSetChanged()
                })
            }
            "sevenDays" -> {
                viewModel.monthSongs.observe(this, Observer<List<RankAlbum>> { t ->
                    adapter.dataList.addAll(t!!)
                    adapter.notifyDataSetChanged()
                })
            }
            else -> {
                viewModel.allSongs.observe(this, Observer<List<RankAlbum>> { t ->
                    adapter.dataList.addAll(t!!)
                    adapter.notifyDataSetChanged()
                })
            }
        }
        viewModel.album(type)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rec_list)
        adapter.onItemClickListener = object : OnItemClickListener<RankAlbum> {
            override fun itemClick(position: Int, data: RankAlbum) {

            }
        }
        recList.adapter = adapter
    }

    companion object {
        fun newInstance(type: String): AlbumRankFragment {
            val bundle = Bundle()
            bundle.putString("type", type)
            val fragment = AlbumRankFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}