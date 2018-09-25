package com.kugou.commom.framwork

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

abstract class FrameworkActivity : AppCompatActivity() {
    var fragmentStackManager = FragmentStackManager()
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentStackManager = FragmentStackManager()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentStackManager.push(null)
    }

    override fun onBackPressed() {
        if (!fragmentStackManager.onBackPress()) {
            super.onBackPressed()
        }
    }
}