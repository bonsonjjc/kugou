package com.kugou.view.ui

import android.content.Intent
import android.os.Bundle
import com.kugou.R
import com.kugou.commom.framwork.FrameworkActivity
import com.kugou.player.PlayerBackUtils

class SplashActivity : FrameworkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        PlayerBackUtils.connect(applicationContext)
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
