package com.kugou.view.ui.fragment.netmusic.special.similar

import android.content.Context
import com.kugou.R
import com.kugou.commom.binding.CommBindingAdapter
import com.kugou.repository.netmusic.special.Special
import com.kugou.repository.netmusic.special.SpecialChild
import com.kugou.utils.KgUtils
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class SimilarAdapter(context: Context) : HelperRecyclerViewAdapter<Special>(context, R.layout.item_special_similar) {
    override fun HelperBindData(viewHolder: HelperRecyclerViewHolder, position: Int, item: Special) {
        viewHolder.setText(R.id.tv_title, item.specialname)
        viewHolder.setText(R.id.tv_listener_count, KgUtils.numberTo(item.playcount))
        CommBindingAdapter.setUrl(viewHolder.getView(R.id.imageView), item.imgurl)
    }
}