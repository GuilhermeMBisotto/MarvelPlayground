package com.guilhermembisotto.core.utils

import android.util.DisplayMetrics
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.guilhermembisotto.core.utils.extensions.convertDpToPx
import kotlin.math.abs

class CustomPagerTransformation(
    private val dipValue: Int,
    private val displayMetrics: DisplayMetrics,
    private val offsetPx: Int,
    private val pageMarginPx: Int,
    private val type: AnimationType
) : ViewPager2.PageTransformer {

    companion object {
        enum class AnimationType {
            TRANSLATION_Y, NONE
        }

        private var maxTranslateOffsetX: Int = 0
    }

    init {
        maxTranslateOffsetX = dipValue.convertDpToPx(displayMetrics)
    }

    override fun transformPage(page: View, position: Float) {
        if (page.parent.parent is ViewPager2) {
            val viewPager = page.parent.parent as ViewPager2
            when (type) {
                AnimationType.TRANSLATION_Y -> {
                    translationYTransformation(page, viewPager, position)
                }
                AnimationType.NONE -> {
                }
            }
        }
    }

    private fun translationYTransformation(page: View, viewPager: ViewPager2, position: Float) {
        page.setPadding(0, dipValue.convertDpToPx(displayMetrics) / 2, 0, 0)
        val offset = position * -(2 * offsetPx + pageMarginPx)

        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            val centerXInViewPager = (page.left - viewPager.scrollX) + page.measuredWidth / 2
            val offsetRate =
                (centerXInViewPager - viewPager.measuredWidth / 2) * 0.38F / viewPager.measuredWidth
            val scaleFactor = 0.5F - abs(offsetRate)

            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
            if (scaleFactor > 0) {
                page.translationY = 0F
                page.translationY = -(dipValue.convertDpToPx(displayMetrics)) * scaleFactor
            }
        } else {
            page.translationY = offset
        }
    }
}
