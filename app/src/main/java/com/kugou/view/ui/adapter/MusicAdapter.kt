package com.kugou.view.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kugou.R
import com.kugou.audio.model.Music
import com.kugou.databinding.ItemSongBinding
import com.kugou.player.PlayerBackUtils

class MusicAdapter(context: Context, val type: Int = 0) : BindingAdapter<Music, ItemSongBinding>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemSongBinding> {
        val view = ItemSongBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemSongBinding>, position: Int) {
        val data = dataList[position]
        holder.binding.data = data

        holder.itemView.setOnClickListener {
            onItemClickListener?.itemClick(position, data)
        }
        holder.binding.btnSelect.setOnClickListener {
            PlayerBackUtils.add(-1, arrayListOf(data))
        }
        holder.binding.viewPlay.isVisible = PlayerBackUtils.playMusic()?.hash == data.hash
        single(holder, position, data)
        holder.binding.executePendingBindings()
    }

    fun single(holder: ViewHolder<ItemSongBinding>, position: Int, data: Music) {
        when (type) {
            1, 2 -> {
                holder.binding.groupSort.isVisible = true
                holder.binding.tvRankSingleSales.isVisible = type == 2

                val rank = rank(position)
                holder.binding.imgRank.setImageResource(rank.second)
                holder.binding.tvRank.text = rank.first
            }
            else -> {
                holder.binding.groupSort.isGone = true
                holder.binding.tvRankSingleSales.isGone = true
            }
        }
    }

    private fun rank(position: Int): Pair<String, Int> {
        var rank = ""
        var res = 0
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
        return rank to res
    }
}