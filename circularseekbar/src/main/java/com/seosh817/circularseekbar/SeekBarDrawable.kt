package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

abstract class SeekBarDrawable(protected val startAngle: Float,
                               protected val sweepAngle: Float) {

    abstract fun draw(canvas: Canvas, rect: RectF, paint: Paint)
}
