package com.kugou.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.SeekBar

class KGSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SeekBar(context, attrs, defStyleAttr) {
    internal var isDisableTabAndDrag = false
    internal var isPreventTapping = false
    private var byDrag = false
    internal var showClimaxPoint = false

    internal var climaxPointPosPercentage = 103

    internal var paint = Paint()
    internal var climaxRadius = 0
    internal var paintX = 0
    internal var paintY = 0

    internal var climaxWidth = 0

    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        setBackgroundColor(Color.TRANSPARENT)
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (showClimaxPoint) {
            canvas.drawCircle((paintX + paddingLeft).toFloat(), paintY.toFloat(), (climaxRadius + dp2px(0.5f)).toFloat(), paint)
        }
    }

    @Synchronized
    override fun setSecondaryProgress(secondaryProgress: Int) {
        if (secondaryProgress < getSecondaryProgress()) return
        super.setSecondaryProgress(max * secondaryProgress / 100)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        paintY = measuredHeight / 2
        val measuredWidth = measuredWidth - paddingLeft - paddingRight
        climaxRadius = suggestedMinimumHeight
        climaxWidth = measuredWidth / max
        paintX = climaxWidth * climaxPointPosPercentage
    }

    fun setPlayedProgressColor(color: Int) {
        val drawable = progressDrawable as LayerDrawable
        val secondaryProgress = drawable.findDrawableByLayerId(android.R.id.secondaryProgress)
        secondaryProgress?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        invalidate()
    }

    fun setProgressBackgroundColor(color: Int) {
        val drawable = progressDrawable as LayerDrawable
        val secondaryProgress = drawable.findDrawableByLayerId(android.R.id.background)
        secondaryProgress?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        invalidate()
    }

    fun setClimaxPointPosPercentage(climaxPointPosPercentage: Int) {
        this.climaxPointPosPercentage = climaxPointPosPercentage
        showClimaxPoint = climaxPointPosPercentage > 0
        if (climaxWidth >= 0) {
            paintY = measuredHeight / 2
            val measuredWidth = measuredWidth - paddingLeft - paddingRight
            climaxRadius = suggestedMinimumHeight
            climaxWidth = measuredWidth / max
            paintX = climaxWidth * climaxPointPosPercentage
        }
        if (climaxPointPosPercentage != this.climaxPointPosPercentage)
            invalidate()
    }

    fun setPreventTapping(preventTapping: Boolean) {
        isPreventTapping = preventTapping
    }

    fun setThumb(normalColor: Int, pressColor: Int) {
        val thumb = thumb
        println()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isDisableTabAndDrag) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
//                Log.e("KGSeekbar", "down")
                isPressed = true
                invalidate()
                if (pointInClimaxPoint(event.x.toInt(), event.y.toInt())) {
//                    Log.e("KGSeekbar", "ClimaxClick")
                }
                if (isPreventTapping && !pointInThumb(event.x.toInt(), event.y.toInt())) {
                    isPressed = false
                    invalidate()
                    return false
                }
//                Log.e("KGSeekbar", "move")
                //                Toast.makeText(getContext(), "progress:" + getProgress(), Toast.LENGTH_SHORT).show();
                byDrag = true
            }
            MotionEvent.ACTION_MOVE -> {
//                Log.e("KGSeekbar", "move")
                byDrag = true
            }
            MotionEvent.ACTION_UP -> {
                isPressed = false
                invalidate()
//                Log.e("KGSeekbar", "up")
                if (isPreventTapping && !byDrag) {
                    invalidate()
                }
                byDrag = false
            }
            MotionEvent.ACTION_CANCEL -> byDrag = false
        }
        return super.onTouchEvent(event)
    }

    private fun pointInThumb(x: Int, y: Int): Boolean {
        val bounds = Rect(thumb.bounds)
        bounds.inset(dp2px(2f), 0)
        return bounds.contains(x, y)
    }

    private fun pointInClimaxPoint(x: Int, y: Int): Boolean {
        val avg = climaxRadius
        val rect = Rect(paintX - avg, paintY - avg, paintX + avg, paintY + avg)
        return rect.contains(x, y)
    }

    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }

    open class AbsOnSeekBarChangeListener : OnSeekBarChangeListener {
        var progress: Int = 0
            private set

        override fun onProgressChanged(seekBar: SeekBar, p: Int, fromUser: Boolean) {
            progress = p
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            progress = seekBar.progress
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            progress = seekBar.progress
        }

    }
}
