package com.kugou.commom.framwork

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class FrameworkFragment : androidx.fragment.app.Fragment() {
    abstract val layoutId: Int

    val className: String by lazy { javaClass.simpleName }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("$className:onCreateView")
        return inflater.inflate(layoutId, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("$className:onViewCreated")
        view.setOnTouchListener { v, event -> true }
    }

    fun fragmentsManager(): FragmentStackManager {
        return (activity as FrameworkActivity).fragmentStackManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        System.gc()
    }

    @CallSuper
    open override fun onAttach(context: Context) {
        super.onAttach(context)
        println("$className:onAttach")
        fragmentsManager().push(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("$className:onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        println("$className:onViewStateRestored")
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        println("$className:onDetach")
    }
}