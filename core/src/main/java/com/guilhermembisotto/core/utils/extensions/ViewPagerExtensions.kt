package com.guilhermembisotto.core.utils.extensions

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.disallowIntercept() {
    this.apply {
        setOnTouchListener { view, _ ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }
}
