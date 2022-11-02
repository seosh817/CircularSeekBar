package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.seosh817.circularseekbar.Constants.START_ANGLE_OFFSET

open class ProgressDrawable(
    protected val min: Float,
    protected val max: Float,
    protected val progress: Float,
    startAngle: Float,
    sweepAngle: Float,
) : SeekBarDrawable(startAngle, sweepAngle) {

    private fun getProgressAngle() = MathUtils.lerp(MathUtils.lerpRatio(min, max, progress), sweepAngle)

    override fun draw(canvas: Canvas, rect: RectF, paint: Paint) {
        canvas.drawArc(
            rect,
            START_ANGLE_OFFSET + startAngle,
            getProgressAngle(),
            false, paint
        )
    }
}
