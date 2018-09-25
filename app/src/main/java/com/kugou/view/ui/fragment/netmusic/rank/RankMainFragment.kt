package com.kugou.view.ui.fragment.netmusic.rank

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.repository.netmusic.rank.RankTag
import com.kugou.view.ui.adapter.OnItemClickListener
import com.kugou.view.ui.fragment.netmusic.rank.album.AlbumRankMainFragment
import com.kugou.view.ui.fragment.netmusic.rank.single.SingleRankMainFragment
import com.kugou.view.ui.fragment.netmusic.rank.week.RankSongFragment

class RankMainFragment : FrameworkFragment() {
    lateinit var mainAdapter: RankMainAdapter
    lateinit var recList: androidx.recyclerview.widget.RecyclerView
    lateinit var viewModel: RankMainViewModel

    override val layoutId: Int get() = R.layout.layout_list

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainAdapter = RankMainAdapter(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.ranktags.observe(this, object : Observer<List<RankTag>> {
            override fun onChanged(t: List<RankTag>?) {
                if (t?.size != 0) {
                    mainAdapter.dataList.addAll(t!!)
                    mainAdapter.notifyDataSetChanged()
                }
            }
        })
        viewModel.request()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recList = view.findViewById(R.id.rec_list)
        recList.adapter = mainAdapter

        mainAdapter.onItemClickListener = object : OnItemClickListener<RankTag> {
            override fun itemClick(position: Int, data: RankTag) {
                toDetail(data)
            }
        }
    }

    private fun toDetail(data: RankTag) {
        when (data.rankname) {
            "酷狗专辑畅销榜" -> {
                fragmentManager!!.beginTransaction()
                        .hide(this)
                        .add(R.id.container, AlbumRankMainFragment.newInstance(data))
                        .addToBackStack("rankSongs")
                        .commit()
            }
            "酷狗单曲畅销榜" -> {
                fragmentManager!!.beginTransaction()
                        .hide(this)
                        .add(R.id.container, SingleRankMainFragment.newInstance(data))
                        .addToBackStack("rankSongs")
                        .commit()
            }
            else -> {
                fragmentManager!!.beginTransaction()
                        .hide(this)
                        .add(R.id.container, RankSongFragment.newInstance(data))
                        .addToBackStack("rankSongs")
                        .commit()
            }
        }
    }
}