package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.seosh817.circularseekbar.utils.MathUtils.degreesToRadians
import com.seosh817.circularseekbar.utils.MathUtils.lerp
import com.seosh817.circularseekbar.utils.MathUtils.lerpRatio
import kotlin.math.cos
import kotlin.math.sin

class ThumbDrawable(
    private val centerPosition: PointF,
    private val seekBarRadius: Float,
    private val min: Float,
    private val max: Float,
    private val progress: Float,
    private val startAngle: Float,
    private val sweepAngle: Float,
    private val dashSum: DashSum,
    private val innerCircleRadius: Float,
    private val outerCircleRadius: Float,
) {

    constructor(centerPosition: PointF, seekBarRadius: Float, min: Float, max: Float, progress: Float, startAngle: Float, sweepAngle: Float, dashWidth: Float, dashGap: Float, innerCircleRadius: Float, outerCircleRadius: Float) : this(centerPosition, seekBarRadius, min, max, progress, startAngle, sweepAngle, DashSum.of(dashWidth, dashGap), innerCircleRadius, outerCircleRadius)

    init {
        require(innerCircleRadius >= 0 && outerCircleRadius >= 0) { "Thumb circle radius must be more than 0" }
    }

    fun draw(canvas: Canvas, innerPaint: Paint, outerPaint: Paint) {
        val startAngleRadian = degreesToRadians(startAngle)

        val thumbX: Float
        val thumbY: Float
        if (dashSum.canDashed()) {
            val progressRatio = lerpRatio(min, max, progress)
            val fullDashesRatio = dashSum.getFullDashesRatio(sweepAngle, min, max, progress)
            val halfDashRatio = progressRatio - fullDashesRatio

            val totalDashWidth = dashSum.getTotalDashWidth(sweepAngle)
            val halfWidthAngle = halfDashRatio * totalDashWidth
            val fullDashesProgressAngle = dashSum.getFullDashesProgressAngle(sweepAngle, min, max, progress)
            val progressAngle = fullDashesProgressAngle + halfWidthAngle
            val progressAngleRadian = degreesToRadians(progressAngle)

            thumbX = centerPosition.x - sin(startAngleRadian + progressAngleRadian) * seekBarRadius
            thumbY = centerPosition.y + cos(startAngleRadian + progressAngleRadian) * seekBarRadius
        } else {
            val progressAngle = lerp(lerpRatio(min, max, progress), sweepAngle)
            val progressAngleRadian = degreesToRadians(progressAngle)

            thumbX = centerPosition.x - sin(startAngleRadian + progressAngleRadian) * seekBarRadius
            thumbY = centerPosition.y + cos(startAngleRadian + progressAngleRadian) * seekBarRadius
        }

        if (sweepAngle > 0) {
            canvas.drawCircle(thumbX, thumbY, outerCircleRadius, outerPaint)
            canvas.drawCircle(thumbX, thumbY, innerCircleRadius, innerPaint)
        }
    }
}
