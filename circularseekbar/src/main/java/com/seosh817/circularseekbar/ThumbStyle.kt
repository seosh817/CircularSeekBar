package com.seosh817.circularseekbar

import android.graphics.Paint

enum class ThumbStyle(val value: Int) {
    FILL(0), STROKE(1), FILL_AND_STROKE(2);

    fun getPaintStyle(): Paint.Style {
        return when (value) {
            STROKE.value -> Paint.Style.STROKE
            FILL_AND_STROKE.value -> Paint.Style.FILL_AND_STROKE
            else -> Paint.Style.FILL
        }
    }

    companion object {
        fun fromValue(value: Int): ThumbStyle {
            return values()
                .firstOrNull {
                    it.value == value
                } ?: throw IllegalArgumentException("CircularSeekBar ThumbStyle $value is unknown")
        }
    }
}
