package com.kugou.view.ui.fragment.netmusic.singer.classify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.singer.SingerGroup
import com.kugou.utils.KgUtils
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter

class ClassifyAdapter(context: Context) : GroupedRecyclerViewAdapter<SingerGroup>(context) {
    val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onBindHeaderViewHolder(holder: HelperRecyclerViewHolder, groupPosition: Int, item: SingerGroup) {
        holder.setText(R.id.tv_title, item.title)
    }

    override fun hasHeader(groupPosition: Int): Boolean {
        return true
    }

    override fun getHeaderLayout(viewType: Int): Int {
        return R.layout.item_singer_group
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return getGroup(groupPosition).singer?.size ?: 0
    }

    override fun onBindChildViewHolder(holder: HelperRecyclerViewHolder, groupPosition: Int, childPosition: Int, item: SingerGroup) {
        val icon = holder.getView<ImageView>(R.id.img_singer_icon)
        val data = item.singer!![childPosition]
        CommBindingAdapter.setPlaceholder(icon, data.imgurl, R.drawable.ico_singer_default)
        holder.setText(R.id.tv_song_title, data.singername)
        holder.setText(R.id.tv_song_singer, KgUtils.heatFans(data.fanscount, data.heat))
        holder.getView<View>(R.id.group_sort).isGone = true
    }

    override fun getFooterLayout(viewType: Int): Int {
        return 0
    }

    override fun hasFooter(groupPosition: Int): Boolean {
        return false
    }

    override fun getChildLayout(viewType: Int): Int {
        return R.layout.item_singer
    }

    override fun onBindFooterViewHolder(holder: HelperRecyclerViewHolder, groupPosition: Int, item: SingerGroup) {

    }

    override fun getGroupCount(): Int {
        return groups.size
    }
}