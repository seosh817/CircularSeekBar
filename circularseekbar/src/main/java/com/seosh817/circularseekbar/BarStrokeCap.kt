package com.seosh817.circularseekbar

import android.graphics.Paint

enum class BarStrokeCap(val value: Int) {

    BUTT(0),
    ROUND(1),
    SQUARE(2);

    fun getPaintStrokeCap(): Paint.Cap {
        return when(value) {
            BUTT.value -> Paint.Cap.BUTT
            SQUARE.value -> Paint.Cap.SQUARE
            else -> Paint.Cap.ROUND
        }
    }

    companion object {
        fun fromValue(value: Int): BarStrokeCap {
            return values().firstOrNull {
                it.value == value
            } ?: throw IllegalArgumentException("BarStrokeCap $value is unknown value")
        }
    }
}
