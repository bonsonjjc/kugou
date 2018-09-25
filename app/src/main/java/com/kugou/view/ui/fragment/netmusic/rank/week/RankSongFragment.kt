package com.kugou.view.ui.fragment.netmusic.rank.week

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.blankj.rxbus.RxBus
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.player.manager.Action
import com.kugou.player.manager.PlayAction
import com.kugou.audio.model.Music
import com.kugou.repository.netmusic.rank.RankTag
import com.kugou.repository.netmusic.rank.RankYear
import com.kugou.view.ui.adapter.MusicAdapter
import com.kugou.view.ui.adapter.OnItemClickListener
import com.kugou.view.ui.dialog.WeekSelectDialog
import com.kugou.view.ui.fragment.netmusic.rank.RankFragment
import io.reactivex.android.schedulers.AndroidSchedulers

class RankSongFragment : RankFragment() {
    lateinit var adapter: MusicAdapter
    lateinit var viewModel: RankSongViewModel

    override val contentLayoutId: Int = R.layout.layout_select_list

    lateinit var rankYears: ArrayList<RankYear>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        RxBus.getDefault().subscribe(this, AndroidSchedulers.mainThread(), object : RxBus.Callback<Action> {
            override fun onEvent(action: Action) {
                if (action.action == PlayAction.PLAY_ACTION_MUSIC) {
                    adapter.notifyDataSetChanged()
                }
            }
        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.init(rankTag.ranktype, rankTag.rankid)

        adapter = MusicAdapter(context, 1)
        viewModel.dataList.observe(this, object : Observer<List<Music>> {
            override fun onChanged(t: List<Music>?) {
                adapter.dataList.addAll(t!!)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.cycle.observe(this, Observer<String> {
            tvCycle.text = it
        })
        viewModel.weekData.observe(this, Observer<List<RankYear>> {
            rankYears = ArrayList()
            rankYears.addAll(it!!)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rec_list)
        recList.adapter = adapter

        view.findViewById<TextView>(R.id.tv_cycle).setOnClickListener {
            WeekSelectDialog.newInstance(rankYears).show(fragmentManager, "select")
        }
        adapter.onItemClickListener = object : OnItemClickListener<Music> {
            override fun itemClick(position: Int, data: Music) {
                viewModel.play(position)
            }
        }

        CommBindingAdapter.setUrl(imgCover, rankTag.banner7url)
        if (rankTag.isvol == 0) {
            viewModel.list()
        } else {
            viewModel.rankCycle()
        }
    }

    override fun onDetach() {
        super.onDetach()
        RxBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(rankTag: RankTag): RankSongFragment {
            val bundle = Bundle()
            bundle.putParcelable("tag", rankTag)
            val fragment = RankSongFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}