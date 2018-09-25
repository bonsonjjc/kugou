package com.kugou.view.ui.fragment.netmusic.special

import android.content.Context
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.special.SpecialGroup
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter

class SpecialAdapter(context: Context) : GroupedRecyclerViewAdapter<SpecialGroup>(context) {
    override fun hasHeader(groupPosition: Int): Boolean {
        return true
    }

    override fun hasFooter(groupPosition: Int): Boolean {
        return false
    }

    override fun getGroupCount(): Int {
        return groups.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return groups[groupPosition].children?.size ?: 0
    }

    override fun onBindHeaderViewHolder(holder: HelperRecyclerViewHolder, groupPosition: Int, item: SpecialGroup) {
        holder.setText(R.id.tv_title, item.name)

    }

    override fun onBindChildViewHolder(holder: HelperRecyclerViewHolder, groupPosition: Int, childPosition: Int, item: SpecialGroup) {
        val child = item.children!![childPosition]
        holder.setText(R.id.tv_title, child.name)
        CommBindingAdapter.setPlaceholder(holder.getView(R.id.img_icon), child.bannerurl, R.drawable.ico_rank_default)
    }

    override fun onBindFooterViewHolder(holder: HelperRecyclerViewHolder?, groupPosition: Int, item: SpecialGroup?) {

    }

    override fun getHeaderLayout(viewType: Int): Int {
        return R.layout.item_special_group
    }

    override fun getFooterLayout(viewType: Int): Int {
        return 0
    }


    override fun getChildLayout(viewType: Int): Int {
        return R.layout.item_special
    }
}
