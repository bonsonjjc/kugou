package com.kugou.view.ui.fragment.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.toast
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import kotlinx.android.synthetic.main.fragment_search_main.*

class SearchFragment : FrameworkFragment() {
    override val layoutId: Int = R.layout.fragment_search_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_left.setOnClickListener {
            activity?.onBackPressed()
        }
        tv_search.setOnClickListener {
            context?.toast(edt_search.text.toString())
        }
    }
}