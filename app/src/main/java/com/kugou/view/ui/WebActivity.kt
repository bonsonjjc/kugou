package com.kugou.view.ui

import android.os.Bundle
import com.kugou.R
import com.kugou.commom.framwork.FrameworkActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : FrameworkActivity() {

    val url: String by lazy { intent.getStringExtra("url") }
    val title: String by lazy { intent.getStringExtra("title") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        webview.loadUrl(url)
        webview.settings.javaScriptEnabled = true
        tv_title.text = title
        btn_left.setOnClickListener {
            onBackPressed()
        }
    }
}