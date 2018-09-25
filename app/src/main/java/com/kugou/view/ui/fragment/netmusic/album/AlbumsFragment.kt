package com.kugou.view.ui.fragment.netmusic.album

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import com.kugou.repository.netmusic.album.Album
import com.kugou.view.ui.adapter.OnItemClickListener

class AlbumsFragment : FrameworkFragment() {
    override val layoutId: Int get() = R.layout.layout_list
    val singerId: Int by lazy { arguments!!.getInt("singerId") }

    lateinit var adapter: AlbumsAdapter
    lateinit var viewModel: AlbumsViewModel

    lateinit var recList: androidx.recyclerview.widget.RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get()
        adapter = AlbumsAdapter(context)
        viewModel.dataList.observe(this, Observer<List<Album>> { t ->
            adapter.dataList.addAll(t!!)
            adapter.notifyDataSetChanged()
        })
        viewModel.onList(singerId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recList = view.findViewById(R.id.rec_list)
        recList.adapter = adapter

        adapter.onItemClickListener = object : OnItemClickListener<Album> {
            override fun itemClick(position: Int, data: Album) {
                fragmentManager!!.beginTransaction()
                        .hide(this@AlbumsFragment)
                        .add(R.id.container, AlbumFragment.newInstance(data))
                        .addToBackStack("album")
                        .commit()
            }
        }
    }

    companion object {
        fun newInstance(singerId: Int): AlbumsFragment {
            val bundle = Bundle()
            bundle.putInt("singerId", singerId)
            val fragment = AlbumsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}