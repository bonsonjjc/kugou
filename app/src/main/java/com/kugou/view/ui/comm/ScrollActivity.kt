package com.kugou.view.ui.comm

import android.os.Bundle
import android.view.ViewStub
import com.kugou.R
import com.kugou.commom.framwork.FrameworkActivity

abstract class ScrollActivity : FrameworkActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_base)

        val headInfalter: ViewStub = findViewById(R.id.layout_header) as ViewStub
        headInfalter.layoutResource = headLayoutId
        headInfalter.inflate()

        val contentInfalter: ViewStub = findViewById(R.id.layout_content) as ViewStub
        contentInfalter.layoutResource = contentLayoutId
        contentInfalter.inflate()
    }

    abstract val headLayoutId: Int
    abstract val contentLayoutId: Int

}
