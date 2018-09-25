package com.kugou.view.ui.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

abstract class BindingAdapter<Data, V : ViewDataBinding>(val context: Context) : RecyclerView.Adapter<ViewHolder<V>>() {
    var dataList: MutableList<Data> = mutableListOf()

    val inflater: LayoutInflater

    var onItemClickListener: OnItemClickListener<Data>? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

interface OnItemClickListener<Data> {
    fun itemClick(position: Int, data: Data)
}
