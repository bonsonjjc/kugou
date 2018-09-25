package com.kugou.view.ui.fragment.netmusic.singer.songs

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
import com.kugou.view.ui.adapter.OnItemClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_select_list.*

class SongFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.layout_select_list

    lateinit var viewModel: SongViewModel
    lateinit var adapter: MusicAdapter
    val url: String by lazy { arguments!!.getString("url") }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MusicAdapter(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        viewModel.list(url)
        viewModel.dataList.observe(this, object : Observer<List<Music>> {
            override fun onChanged(t: List<Music>?) {
                adapter.dataList.addAll(t!!)
                adapter.notifyDataSetChanged()
            }
        })
        RxBus.getDefault().subscribe(this, AndroidSchedulers.mainThread(), object : RxBus.Callback<Action> {
            override fun onEvent(action: Action) {
                if (action.action == PlayAction.PLAY_ACTION_MUSIC) {
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rec_list.adapter = adapter

        adapter.onItemClickListener = object : OnItemClickListener<Music> {
            override fun itemClick(position: Int, data: Music) {
                viewModel.play(position)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        RxBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(singerId: Int): SongFragment {
            val url = "v3/singer/song?sorttype=2&version=8983&plat=0&singerid=$singerId&area_code=1"
            return newInstance(url)
        }

        fun album(albumId: Int): SongFragment {
            val url = "http://mobilecdnbj.kugou.com/api/v3/album/song?version=8983&albumid=$albumId&plat=0&area_code=1"
            return newInstance(url)
        }

        fun special(specialId: Int): SongFragment {
            val url = "http://mobilecdngz.kugou.com/api/v3/special/song?specialid=$specialId&plat=2&version=8980&area_code=1"
            return newInstance(url)
        }

        fun newInstance(url: String): SongFragment {
            val bundle = Bundle()
            bundle.putString("url", url)
            val fragment = SongFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}