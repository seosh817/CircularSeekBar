package com.seosh817.circularseekbar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.seosh817.circularseekbar.Constants.START_ANGLE_OFFSET

open class TrackDrawable(
    startAngle: Float,
    sweepAngle: Float,
) : SeekBarDrawable(startAngle, sweepAngle) {

    override fun draw(canvas: Canvas, rect: RectF, paint: Paint) {
        canvas.drawArc(
            rect,
            START_ANGLE_OFFSET + startAngle,
            sweepAngle,
            false, paint
        )
    }
}
