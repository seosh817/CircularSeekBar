package com.seosh817.circularseekbar

import android.view.animation.*

enum class CircularSeekBarAnimation(val value: Int) {

    NORMAL(0),
    BOUNCE(1),
    DECELERATE(2),
    ACCELERATE_DECELERATE(3);

    fun getInterpolator(): Interpolator {
        return when (value) {
            NORMAL.value -> LinearInterpolator()
            BOUNCE.value -> BounceInterpolator()
            DECELERATE.value -> DecelerateInterpolator()
            ACCELERATE_DECELERATE.value -> AccelerateDecelerateInterpolator()
            else -> LinearInterpolator()
        }
    }

    companion object {
        fun fromValue(value: Int): CircularSeekBarAnimation {
            return values().firstOrNull {
                it.value == value
            } ?: throw IllegalArgumentException("CircularSeekBarAnimation $value is unknown value")
        }
    }
}