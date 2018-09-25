package com.kugou.view.ui.fragment.mv

import android.content.Context
import android.view.ViewGroup
import com.kugou.databinding.ItemSingerMvBinding
import com.kugou.repository.mv.Video
import com.kugou.view.ui.adapter.BindingAdapter
import com.kugou.view.ui.adapter.ViewHolder

class MvAdapter(context: Context) : BindingAdapter<Video, ItemSingerMvBinding>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemSingerMvBinding> {
        return ViewHolder(ItemSingerMvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemSingerMvBinding>, position: Int) {
        val data = dataList[position]
        holder.binding.data = data
    }
}
