package com.kugou.view.ui.dialog

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.kugou.R
import com.kugou.audio.model.Music
import com.kugou.player.PlayerBackUtils
import com.kugou.utils.KgUtils
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewDragAdapter
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class PlayListAdapter(context: Context) : HelperRecyclerViewDragAdapter<Music>(context, R.layout.item_playlist) {
    override fun HelperBindData(viewHolder: HelperRecyclerViewHolder, position: Int, item: Music) {
        val songs = KgUtils.bar(item.filename)
        viewHolder.setText(R.id.tv_title, songs.first)
        viewHolder.setText(R.id.tv_singer, songs.second)
        val tvSort = viewHolder.getView<TextView>(R.id.tv_sort)
        val icon = viewHolder.getView<ImageView>(R.id.img_icon)
        if (PlayerBackUtils.playMusic()?.hash == item.hash && PlayerBackUtils.playBack().playIndex == position) {
            viewHolder.setTextColorRes(R.id.tv_title, R.color.theme)
            viewHolder.setTextColorRes(R.id.tv_singer, R.color.theme)
            icon.isInvisible = false
            icon.setImageResource(R.drawable.ico_singer_default)
            viewHolder.getView<View>(R.id.btn_download).isVisible = true
            viewHolder.getView<View>(R.id.btn_like).isVisible = true
            tvSort.text = ""
        } else {
            viewHolder.setTextColorRes(R.id.tv_title, R.color.black)
            viewHolder.setTextColorRes(R.id.tv_singer, R.color.gray)
            tvSort.text = String.format("%02d", position + 1)
            icon.isInvisible = true
            viewHolder.getView<View>(R.id.btn_download).isGone = true
            viewHolder.getView<View>(R.id.btn_like).isGone = true
        }
    }
}