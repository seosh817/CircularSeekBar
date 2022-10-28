package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px

class TrackView(
    context: Context
) : ProgressViewBase(context) {

    override var centerPosition: PointF by progressViewProperty(PointF())
    override var radiusPx: Float by progressViewProperty(0f)

    /** The Angle to start drawing [CircularSeekBar] from. */
    var startAngle: Float by progressViewProperty(0f)

    /** The Angle through which to draw [CircularSeekBar] bar. */
    var sweepAngle: Float by progressViewProperty(180f)

    /** The thickness of [CircularSeekBar]. */
    @get:Px
    var barWidth: Float by progressViewProperty(dp2Px(8).toFloat())

    /** Styles to use for arcs endings. */
    var barStrokeCap: BarStrokeCap by progressViewProperty(BarStrokeCap.ROUND)

    /** Dash width of seek bar. */
    var dashWidth: Float by progressViewProperty(0f)

    /** Dash gap of seek bar. */
    var dashGap: Float by progressViewProperty(0f)

    /** Background track color of [CircularSeekBar]. */
    @get:ColorInt
    var trackColor: Int by progressViewProperty(Color.LTGRAY)

    private var trackPaint = Paint().apply {
        color = trackColor
        strokeWidth = barWidth
        style = Paint.Style.STROKE
        strokeCap = barStrokeCap.getPaintStrokeCap()
        isAntiAlias = true
    }

    private var dashedProgressDrawable = DashedTrackDrawable(DashWidth.from(dashWidth), DashGap.from(dashGap), startAngle, sweepAngle)

    override fun updateView() {
        trackPaint = Paint().apply {
            color = trackColor
            strokeWidth = barWidth
            style = Paint.Style.STROKE
            strokeCap = barStrokeCap.getPaintStrokeCap()
            isAntiAlias = true
        }
        dashedProgressDrawable = DashedTrackDrawable(DashWidth.from(dashWidth), DashGap.from(dashGap), startAngle, sweepAngle)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val rect = RectF(
            centerPosition.x - radiusPx,
            centerPosition.y - radiusPx,
            centerPosition.x + radiusPx,
            centerPosition.y + radiusPx
        )
        canvas?.let {
            dashedProgressDrawable.draw(it, rect, trackPaint)
        }
    }
}