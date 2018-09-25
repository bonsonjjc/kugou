package com.kugou.view.ui.fragment.netmusic.singer.heat

import android.content.Context
import android.view.ViewGroup
import com.kugou.R
import com.kugou.databinding.ItemSingerBinding
import com.kugou.repository.netmusic.singer.Singer
import com.kugou.view.ui.adapter.BindingAdapter
import com.kugou.view.ui.adapter.ViewHolder

class SingerHeatAdapter(context: Context) : BindingAdapter<Singer, ItemSingerBinding>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemSingerBinding> {
        val binding = ItemSingerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemSingerBinding>, position: Int) {
        val data = dataList[position]
        var res = 0
        var rank = ""
        when (position) {
            0 -> {
                res = R.drawable.ico_rank_first
            }
            1 -> {
                res = R.drawable.ico_rank_second
            }
            2 -> {
                res = R.drawable.ico_rank_third
            }
            else -> {
                rank = "${position + 1}"
            }
        }
        holder.binding.setData(data)
        holder.binding.executePendingBindings()
        holder.binding.tvRanking.text = rank
        holder.binding.imgRank.setImageResource(res)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.itemClick(position, data)
        }
    }
}
