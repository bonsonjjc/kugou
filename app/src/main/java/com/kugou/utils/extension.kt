package com.kugou.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue


val androidx.recyclerview.widget.RecyclerView.ViewHolder.context: Context
    get() = itemView.context

fun Context.drawable(res: Int): Drawable? = ContextCompat.getDrawable(this, res)
fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

fun Context.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

fun Context.screenDpi(): PointF {
    val xdpi = resources.displayMetrics.xdpi
    val ydpi = resources.displayMetrics.ydpi
    return PointF(xdpi, ydpi)
}

fun Context.screen(): Point {
    val xdpi = resources.displayMetrics.widthPixels
    val ydpi = resources.displayMetrics.heightPixels
    return Point(xdpi, ydpi)
}
