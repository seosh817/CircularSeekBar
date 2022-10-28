package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

open class TrackDrawable(
    startAngle: Float,
    sweepAngle: Float,
) : SeekBarDrawable(startAngle, sweepAngle) {

    override fun draw(canvas: Canvas, rect: RectF, paint: Paint) {
        canvas.drawArc(
            rect,
            startAngle,
            sweepAngle,
            false, paint
        )
    }
}
