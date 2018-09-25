package com.kugou.view.widget

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.Scroller

class KGPlayBarMainView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    internal lateinit var leftView: View
    internal lateinit var sliderView: View
    internal lateinit var rightView: View

    private var mMode = MODE_RIGHT

    internal var mVelocityTracker: VelocityTracker? = null
    internal var mScroller: Scroller

    internal var mIsBeingDragged: Boolean = false
    internal var mIntercept: Boolean = false
    internal var mTouchSlop: Int = 0
    private var mLastMotionX: Int = 0
    private var mFirstMotionX: Int = 0
    private var isDragged: Boolean = false
    internal var mActivePointerId = -1

    init {
        val viewConfiguration = ViewConfiguration.get(context)
        mTouchSlop = viewConfiguration.scaledTouchSlop
        mScroller = Scroller(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)

        measureChildWithMargins(sliderView, widthMeasureSpec, 0, heightMeasureSpec, 0)
        val params = sliderView.layoutParams as FrameLayout.LayoutParams

        val widthUsed = sliderView.measuredWidth + params.rightMargin + params.leftMargin
        val maxWidth = width * 2 - widthUsed

        measureChildWithMargins(leftView, widthMeasureSpec, widthUsed, heightMeasureSpec, 0)
        measureChildWithMargins(rightView, widthMeasureSpec, widthUsed, heightMeasureSpec, 0)
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.EXACTLY)
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        println("PlayBarMainView.onFinishInflate")
        leftView = getChildAt(1)
        sliderView = getChildAt(2)
        rightView = getChildAt(3)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        slider(mMode, false)
    }

    @JvmOverloads
    fun slider(mode: Int, isSmooth: Boolean = true) {
        val x: Int
        if (mode == MODE_LEFT) {
            x = 0
            mMode = MODE_LEFT
        } else {
            x = leftView.measuredWidth
            mMode = MODE_RIGHT
        }
        if (isSmooth)
            smoothTo(x, 0)
        else
            scrollTo(x, 0)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        mIntercept = super.dispatchTouchEvent(ev)
        return mIntercept
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("onInterceptTouchEvent", "down:")
                mLastMotionX = ev.x.toInt()
                mFirstMotionX = mLastMotionX
                mActivePointerId = ev.getPointerId(0)
                if (childCount == 0) {
                    return false
                }
                if (isChild(ev.x.toInt(), ev.y.toInt())) {
                    mIsBeingDragged = true
                } else {
                    Log.e("onInterceptTouchEvent", "cancel")
                    mIsBeingDragged = false
                    mActivePointerId = -1
                    recycleVelocityTracker()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                Log.e("onInterceptTouchEvent", "cancel")
                mIsBeingDragged = false
                mActivePointerId = -1
                recycleVelocityTracker()
            }
        }
        return !mIntercept
    }



    fun findView(x: Int, y: Int): View? {
        var view: View
        for (i in childCount - 1 downTo 1) {
            view = getChildAt(i)
            val left = view.left
            val right = view.right
            val top = view.top
            val bottom = view.bottom
            val rect = RectF((left - scrollX).toFloat(), top.toFloat(), (right - scrollX).toFloat(), bottom.toFloat())
            if (rect.contains(x.toFloat(), y.toFloat())) {
                return view
            }
        }
        return null
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        initVelocityTrackerIfNotExists()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("onTouchEvent", "down")
                mLastMotionX = event.x.toInt()
                mFirstMotionX = mLastMotionX
                mActivePointerId = event.getPointerId(0)

                if (mScroller.isFinished) {
                    mScroller.abortAnimation()
                }
                mVelocityTracker = VelocityTracker.obtain()
                mVelocityTracker!!.addMovement(event)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("onTouchEvent", "move")
                if (mActivePointerId == -1) {
                    mVelocityTracker!!.clear()
                } else {
                    mVelocityTracker!!.addMovement(event)
                    val x = event.getX(mActivePointerId).toInt()
                    var xDiff = mLastMotionX - x
                    if (Math.abs(x - mLastMotionX) > mTouchSlop) {
                        isDragged = true
                        if (mIsBeingDragged) {
                            if (scrollX + xDiff <= 0) {
                                xDiff = 0
                            } else if (scrollX + xDiff >= leftView.measuredWidth) {
                                xDiff = 0
                            }
                            scrollBy(xDiff, 0)
                        }
                    }
                    mLastMotionX = x
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!isDragged && Math.abs(mLastMotionX - mFirstMotionX) < 5) {
                    performClick()
                }
                Log.e("onTouchEvent", "up")
                if (mIsBeingDragged) {
                    val velocityTracker = mVelocityTracker
                    velocityTracker!!.computeCurrentVelocity(1000, 8000f)
                    val initialVelocity = velocityTracker.getXVelocity(mActivePointerId).toInt()
                    if (mMode == MODE_RIGHT) {
                        if (scrollX < leftView.measuredWidth / 2 || initialVelocity > 2000) {
                            mMode = MODE_LEFT
                        }
                    } else if (mMode == MODE_LEFT) {
                        if (scrollX > leftView.measuredWidth / 2 || initialVelocity < -2000) {
                            mMode = MODE_RIGHT
                        }
                    }
                    slider(mMode)
                }
                mIsBeingDragged = false
                mActivePointerId = -1
                isDragged = false
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.e("onTouchEvent", "up")
                if (mIsBeingDragged) {
                    val velocityTracker = mVelocityTracker
                    velocityTracker!!.computeCurrentVelocity(1000, 8000f)
                    val initialVelocity = velocityTracker.getXVelocity(mActivePointerId).toInt()
                    if (mMode == MODE_RIGHT) {
                        if (scrollX < leftView.measuredWidth / 2 || initialVelocity > 2000) {
                            mMode = MODE_LEFT
                        }
                    } else if (mMode == MODE_LEFT) {
                        if (scrollX > leftView.measuredWidth / 2 || initialVelocity < -2000) {
                            mMode = MODE_RIGHT
                        }
                    }
                    slider(mMode)
                }
                mIsBeingDragged = false
                mActivePointerId = -1
                isDragged = false
            }
        }
        return true
    }


    private fun isChild(x: Int, y: Int): Boolean {
        return findView(x, y) === sliderView
    }

    fun smoothTo(x: Int, y: Int) {
        smoothBy(x - scrollX, y - scrollY)
    }

    fun smoothBy(dx: Int, dy: Int) {
        mScroller.startScroll(scrollX, scrollY, dx, dy, 500)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    companion object {

        private val MODE_LEFT = 0
        private val MODE_RIGHT = 1
    }

}
