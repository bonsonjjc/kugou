package com.kugou.view.ui.comm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment

abstract class ScrollFragment : FrameworkFragment() {

    override val layoutId: Int = R.layout.layout_base

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        val headInflater = view.findViewById<ViewStub>(R.id.layout_header)
        headInflater.layoutResource = headLayoutId
        headInflater.inflate()

        val contentInflater = view.findViewById<ViewStub>(R.id.layout_content)
        contentInflater.layoutResource = contentLayoutId
        contentInflater.inflate()

        if (toolbarLayoutId != 0) {
            val toolbarInflater = view.findViewById<ViewStub>(R.id.layout_toolbar)
            toolbarInflater.layoutResource = toolbarLayoutId
            toolbarInflater.inflate()
        }
        return view
    }

    abstract val headLayoutId: Int
    abstract val contentLayoutId: Int
    abstract val toolbarLayoutId: Int
}