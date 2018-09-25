package com.kugou.view.ui.fragment.netmusic.mv.all

import android.content.Context
import android.view.ViewGroup
import com.kugou.databinding.ItemMvBinding
import com.kugou.audio.model.Music
import com.kugou.view.ui.adapter.BindingAdapter
import com.kugou.view.ui.adapter.ViewHolder

class AllMvAdapter(context: Context) : BindingAdapter<Music, ItemMvBinding>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemMvBinding> {
        return ViewHolder(ItemMvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemMvBinding>, position: Int) {

    }
}