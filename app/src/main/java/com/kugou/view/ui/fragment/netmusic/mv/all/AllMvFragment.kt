package com.kugou.view.ui.fragment.netmusic.mv.all

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment

class AllMvFragment : FrameworkFragment() {

    override val layoutId: Int = R.layout.layout_list
    lateinit var adapter: AllMvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AllMvAdapter(context!!)
        val recList = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rec_list)
        recList.adapter = adapter
    }
}