package com.kugou.view.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class ViewHolder<Binding : ViewDataBinding>(val binding: Binding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

