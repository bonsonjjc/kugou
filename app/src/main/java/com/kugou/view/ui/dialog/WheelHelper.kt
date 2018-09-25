package com.kugou.view.ui.dialog

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class WheelHelper : LinearSnapHelper() {
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        println("WheelHelper.calculateDistanceToFinalSnap")
        return super.calculateDistanceToFinalSnap(layoutManager, targetView)
    }

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        println("WheelHelper.findTargetSnapPosition")
        return super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        println("WheelHelper.findSnapView")
        return super.findSnapView(layoutManager)
    }
}