package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.seosh817.circularseekbar.MathUtils.lerp
import com.seosh817.circularseekbar.MathUtils.lerpRatio

class ProgressView(context: Context) : ProgressViewBase(context) {

    override var centerPosition: PointF by progressViewProperty(PointF())
    override var radiusPx: Float by progressViewProperty(0f)

    /** Current value of [CircularSeekBar]. */
    var progress: Float by progressViewProperty(0f)

    /** Minimum value of [CircularSeekBar]. */
    var min: Float by progressViewProperty(0f)

    /** Maximum value of [CircularSeekBar]. */
    var max: Float by progressViewProperty(100f)

    /** The Angle to start drawing [CircularSeekBar] from. */
    var startAngle: Float by progressViewProperty(0f)

    /** The Angle through which to draw [CircularSeekBar] bar. */
    var sweepAngle: Float by progressViewProperty(180f)

    /** The thickness of [CircularSeekBar]. */
    @get:Px
    var barWidth: Float by progressViewProperty(dp2Px(8).toFloat())

    /** Foreground progress color of [CircularSeekBar]. */
    @get:ColorInt
    var progressColor: Int by progressViewProperty(Color.parseColor("#FF189BFA"))

    /** Styles to use for arcs endings. */
    var barStrokeCap: BarStrokeCap by progressViewProperty(BarStrokeCap.ROUND)

    /** Set to true if you want to interact with TapDown to change [CircularSeekBar]'s progress. */
    var interactive: Boolean by progressViewProperty(true)

    private var progressPaint = Paint().apply {
        color = progressColor
        strokeWidth = barWidth
        style = Paint.Style.STROKE
        strokeCap = barStrokeCap.getPaintStrokeCap()
        isAntiAlias = true
    }

    override fun updateView() {
        progressPaint = Paint().apply {
            color = progressColor
            strokeWidth = barWidth
            style = Paint.Style.STROKE
            strokeCap = barStrokeCap.getPaintStrokeCap()
            isAntiAlias = true
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val startAngle: Float = startAngle + CircularSeekBar.START_ANGLE_OFFSET
        val progressAngle: Float = lerp(lerpRatio(min, max, progress), sweepAngle)

        val rect = RectF(
            centerPosition.x - radiusPx,
            centerPosition.y - radiusPx,
            centerPosition.x + radiusPx,
            centerPosition.y + radiusPx
        )

        canvas?.drawArc(
            rect,
            startAngle,
            progressAngle,
            false, progressPaint
        )
    }
}