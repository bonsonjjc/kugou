package com.kugou.view.ui.fragment.netmusic.rank.album

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.kugou.R
import com.kugou.databinding.ItemAlbumBestSellingBinding
import com.kugou.repository.netmusic.rank.RankAlbum
import com.kugou.utils.drawable
import com.kugou.view.ui.adapter.BindingAdapter
import com.kugou.view.ui.adapter.ViewHolder

class AlbumAdapter(context: Context) : BindingAdapter<RankAlbum, ItemAlbumBestSellingBinding>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemAlbumBestSellingBinding> {
        val binding = ItemAlbumBestSellingBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemAlbumBestSellingBinding>, position: Int) {
        val rankSong = dataList[position]
        holder.binding.setData(rankSong)
        holder.binding.executePendingBindings()
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
        val change = rankSong.ranking_change
        val tvAlbumRankingChange = holder.binding.tvAlbumRankingChange
        when {
            rankSong.is_new == 1 -> {
                tvAlbumRankingChange.text = "new"
                tvAlbumRankingChange.setTextColor(Color.GREEN)
                tvAlbumRankingChange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
            change > 0 -> {
                tvAlbumRankingChange.text = "${Math.abs(change)}"
                tvAlbumRankingChange.setTextColor(Color.GRAY)
                tvAlbumRankingChange.setCompoundDrawablesWithIntrinsicBounds(context.drawable(R.drawable.ico_rank_rise), null, null, null)
            }
            change < 0 -> {
                tvAlbumRankingChange.text = "${Math.abs(change)}"
                tvAlbumRankingChange.setTextColor(Color.GRAY)
                tvAlbumRankingChange.setCompoundDrawablesWithIntrinsicBounds(context.drawable(R.drawable.ico_rank_fall), null, null, null)
            }
            else -> {
                tvAlbumRankingChange.text = ""
                tvAlbumRankingChange.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }
        holder.binding.imgAlbumRanking.setImageResource(res)
        holder.binding.tvAlbumRanking.text = rank
        holder.itemView.setOnClickListener {
            onItemClickListener?.itemClick(position, rankSong)
        }
    }
}