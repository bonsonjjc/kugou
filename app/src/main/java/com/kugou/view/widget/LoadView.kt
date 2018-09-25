package com.kugou.view.widget

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnPause

import com.kugou.R
import com.makeramen.roundedimageview.RoundedImageView

class LoadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SinkImageView(context, attrs, defStyleAttr) {
    var animator: Animator? = null

    fun start() {
        if (animator == null) {
            animator = AnimatorInflater.loadAnimator(context, R.animator.pic_rote)
            animator?.setTarget(this)
            animator?.interpolator = LinearInterpolator()
        }
        if (animator!!.isStarted.not()) {
            animator?.start()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                animator?.resume()
            } else {
                animator?.start()
            }
        }
    }

    fun stop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator?.pause()
        } else {
            animator?.cancel()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
