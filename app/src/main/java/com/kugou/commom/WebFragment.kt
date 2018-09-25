package com.kugou.commom

import android.os.Bundle
import android.view.View
import com.kugou.R
import com.kugou.commom.framwork.FrameworkFragment
import kotlinx.android.synthetic.main.activity_web.*

class WebFragment : FrameworkFragment() {
    override val layoutId: Int get() = R.layout.fragment_web

    val url: String by lazy { arguments!!.getString("url") }
    val title: String by lazy { arguments!!.getString("title") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadUrl(url)
        webview.settings.javaScriptEnabled = true
        tv_title.text = title
        btn_left.setOnClickListener {
            fragmentsManager().onBackPress()
        }
    }

    companion object {

        fun newInstance(title: String, url: String): WebFragment {
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("url", url)
            val fragment = WebFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}