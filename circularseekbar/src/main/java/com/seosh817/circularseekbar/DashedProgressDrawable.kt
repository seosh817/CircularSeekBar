package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.seosh817.circularseekbar.MathUtils.lerpRatio

class DashedProgressDrawable(
    private val dashSum: DashSum,
    min: Float,
    max: Float,
    progress: Float,
    startAngle: Float,
    sweepAngle: Float,
) : ProgressDrawable(min, max, progress, startAngle, sweepAngle) {

    constructor(dashWidth: Float, dashGap: Float, min: Float, max: Float, progress: Float, startAngle: Float, sweepAngle: Float) : this(DashSum.of(dashWidth, dashGap), min, max, progress, startAngle, sweepAngle)

    private fun canDashed() = dashSum.canDashed()

    private fun getFullProgressRatio(): Float {
        return dashSum.getFullProgressRatio(sweepAngle, min, max, progress)
    }

    private fun calculateProgressDashCounts(): Int {
        return dashSum.getProgressDashCounts(sweepAngle, min, max, progress)
    }

    private fun calculateTotalDashCounts(): Int {
        return dashSum.getTotalDashCounts(sweepAngle)
    }

    private fun drawDashes(canvas: Canvas, rect: RectF, paint: Paint, counts: Int) {
        for (i in 0 until counts) {
            canvas.drawArc(
                rect,
                startAngle + dashSum * i,
                dashSum.dashWidth.value,
                false,
                paint,
            )
        }

        canvas.drawArc(
            rect,
            startAngle + dashSum * calculateProgressDashCounts(),
            dashSum.dashWidth.value * (lerpRatio(min, max, progress) - getFullProgressRatio()) * calculateTotalDashCounts(),
            false,
            paint
        )
    }

    override fun draw(canvas: Canvas, rect: RectF, paint: Paint) {
        if (!canDashed()) {
            super.draw(canvas, rect, paint)
        } else {
            drawDashes(canvas, rect, paint, calculateProgressDashCounts())
        }
    }
}
