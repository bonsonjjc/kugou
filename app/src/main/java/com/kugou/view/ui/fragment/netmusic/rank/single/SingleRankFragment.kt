package com.kugou.view.ui.fragment.netmusic.rank.single

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.blankj.rxbus.RxBus
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.player.manager.Action
import com.kugou.player.manager.PlayAction
import com.kugou.audio.model.Music
import com.kugou.view.ui.adapter.MusicAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_select_list.*

class SingleRankFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.layout_select_list

    lateinit var adapter: MusicAdapter
    lateinit var viewModel: SingleRanklViewModel

    val type: String by lazy { arguments!!.getString("type") }

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
        adapter = MusicAdapter(context, 2)
        when (type) {
            "day" -> {
                viewModel.songs.observe(this, Observer<List<Music>> { t ->
                    adapter.dataList.addAll(t!!)
                    adapter.notifyDataSetChanged()
                })
            }
            "month" -> {
                viewModel.monthSongs.observe(this, Observer<List<Music>> { t ->
                    adapter.dataList.addAll(t!!)
                    adapter.notifyDataSetChanged()
                })
            }
            else -> {
                viewModel.allSongs.observe(this, Observer<List<Music>> { t ->
                    adapter.dataList.addAll(t!!)
                    adapter.notifyDataSetChanged()
                })
            }
        }
        viewModel.single(type)
    }


    override fun onDetach() {
        super.onDetach()
        RxBus.getDefault().unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rec_list.adapter = adapter
    }

    companion object {
        fun newInstance(type: String): SingleRankFragment {
            val bundle = Bundle()
            bundle.putString("type", type)
            val fragment = SingleRankFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}