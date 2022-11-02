package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.seosh817.circularseekbar.Constants.START_ANGLE_OFFSET
import kotlin.math.roundToInt

class DashedTrackDrawable(
    private val dashSum: DashSum,
    startAngle: Float,
    sweepAngle: Float
) : TrackDrawable(startAngle, sweepAngle) {

    constructor(dashWidth: Float, dashGap: Float, startAngle: Float, sweepAngle: Float): this(DashSum.of(dashWidth, dashGap), startAngle, sweepAngle)

    private fun canDashed() = dashSum.canDashed()

    private fun getDashCount(): Int = dashSum.getTotalDashCounts(sweepAngle)

    private fun drawDashes(canvas: Canvas, rect: RectF, paint: Paint, counts: Int) {
        for (i in 0 until counts) {
            canvas.drawArc(
                rect,
                START_ANGLE_OFFSET + startAngle + dashSum * i,
                dashSum.dashWidth.value,
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
