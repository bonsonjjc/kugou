package com.kugou.view.ui.fragment.netmusic.album

import android.content.Context
import android.view.ViewGroup
import com.kugou.view.ui.adapter.BindingAdapter
import com.kugou.view.ui.adapter.ViewHolder
import com.kugou.databinding.ItemAlbumBinding
import com.kugou.repository.netmusic.album.Album

class AlbumsAdapter(context: Context) : BindingAdapter<Album, ItemAlbumBinding>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemAlbumBinding> {
        return ViewHolder(ItemAlbumBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemAlbumBinding>, position: Int) {
        val data = dataList[position]
        holder.binding.setData(data)

        holder.itemView.setOnClickListener {
            onItemClickListener?.itemClick(position, data)
        }
    }
}