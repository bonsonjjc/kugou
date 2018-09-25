package com.kugou.commom

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kugou.commom.framwork.FrameworkFragment

abstract class BindingFragment<Binding : ViewDataBinding> : FrameworkFragment() {
    lateinit var binding: Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("BindingFragment.onViewCreated")
        binding.setLifecycleOwner(this)
        view.setOnTouchListener { v, event -> true }
    }
}