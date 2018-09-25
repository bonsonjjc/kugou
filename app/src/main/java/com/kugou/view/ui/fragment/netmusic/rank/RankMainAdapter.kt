package com.kugou.view.ui.fragment.netmusic.rank

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kugou.view.ui.adapter.BindingAdapter
import com.kugou.view.ui.adapter.ViewHolder
import com.kugou.databinding.ItemRankBinding
import com.kugou.repository.netmusic.rank.RankTag

class RankMainAdapter(context: Context) : BindingAdapter<RankTag, ItemRankBinding>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemRankBinding> {
        val binding = ItemRankBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder<ItemRankBinding>, position: Int) {
        val rankTag = dataList[position]
        holder.binding.setData(rankTag)
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            onItemClickListener?.itemClick(position, rankTag)
        }
        holder.binding.imgPlay.isGone = rankTag.rankname == "酷狗专辑畅销榜"
    }
}
