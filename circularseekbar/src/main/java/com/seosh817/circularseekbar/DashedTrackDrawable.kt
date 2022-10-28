package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.roundToInt

class DashedTrackDrawable(
    private val dashWidth: DashWidth,
    private val dashGap: DashGap,
    startAngle: Float,
    sweepAngle: Float
) : TrackDrawable(startAngle, sweepAngle) {

    private val dashSum: Float
        get() = dashWidth + dashGap

    private fun canDashed() = dashWidth.isAvailable() && dashGap.isAvailable()

    private fun getDashCount(): Int {
        return if (sweepAngle >= (sweepAngle / dashSum) * dashSum + dashWidth.value) {
            (sweepAngle / dashSum).roundToInt() + 1
        } else {
            (sweepAngle / dashSum).roundToInt()
        }
    }

    private fun drawDashes(canvas: Canvas, rect: RectF, paint: Paint, counts: Int) {
        for (i in 0 until counts) {
            canvas.drawArc(
                rect,
                startAngle + dashSum * i,
                dashWidth.value,
                false,
                paint,
            )
        }
    }

    override fun draw(canvas: Canvas, rect: RectF, paint: Paint) {
        if (!canDashed()) {
            super.draw(canvas, rect, paint)
        } else {
            drawDashes(canvas, rect, paint, getDashCount())
        }
    }
}
