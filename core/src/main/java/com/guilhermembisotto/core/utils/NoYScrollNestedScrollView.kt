package com.guilhermembisotto.core.utils

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

class NoYScrollNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    companion object {
        private lateinit var mGestureDetector: GestureDetector
    }

    init {
        mGestureDetector = GestureDetector(context, YScrollDetector())
        setFadingEdgeLength(0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev)
    }

    // Return false if we're scrolling in the x direction
    inner class YScrollDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return abs(distanceY) > abs(distanceX)
        }
    }
}