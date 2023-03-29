package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.seosh817.circularseekbar.extensions.dp2Px
import kotlin.math.min

class TrackView(context: Context) : ProgressViewBase(context) {

    override var centerPosition: PointF by progressViewProperty(PointF(width / 2f, height / 2f))
    override var radiusPx: Float by progressViewProperty(min(width / 2f, height / 2f))

    /** The Angle to start drawing [CircularSeekBar] from. */
    var startAngle: Float by progressViewProperty(90f)

    /** The Angle through which to draw [CircularSeekBar] bar. */
    var sweepAngle: Float by progressViewProperty(360f)

    /** The thickness of [CircularSeekBar]. */
    @get:Px
    var barWidth: Float by progressViewProperty(dp2Px(4).toFloat())

    /** Styles to use for arcs endings. */
    var barStrokeCap: BarStrokeCap by progressViewProperty(BarStrokeCap.ROUND)

    /** Dash width of seek bar. */
    var dashWidth: Float by progressViewProperty(0f)

    /** Dash gap of seek bar. */
    var dashGap: Float by progressViewProperty(0f)

    /** Background trackGradientColors of [CircularSeekBar]. */
    var trackGradientColorsArray: IntArray by progressViewProperty(intArrayOf())

    /** Background track color of [CircularSeekBar]. */
    @get:ColorInt
    var trackColor: Int by progressViewProperty(Color.LTGRAY)

    /** The radius of the [CircularSeekBar] inner thumb. */
    var innerThumbRadius: Float by progressViewProperty(0f)

    /** The stroke width of the [CircularSeekBar] inner thumb. */
    var innerThumbStrokeWidth: Float by progressViewProperty(0f)

    /** The radius of the [CircularSeekBar] outer thumb. */
    var outerThumbRadius: Float by progressViewProperty(0f)

    /** The stroke width of the [CircularSeekBar] outer thumb. */
    var outerThumbStrokeWidth: Float by progressViewProperty(0f)

    private var trackPaint = Paint().apply {
        strokeWidth = barWidth
        style = Paint.Style.STROKE
        strokeCap = barStrokeCap.getPaintStrokeCap()
        isAntiAlias = true
        if (trackGradientColorsArray.size > 1) {
            val sweepGradientShader = createSweepGradient(barWidth, startAngle, dashedTrackDrawable.getPaintedSweepAngle(), trackGradientColorsArray, barStrokeCap == BarStrokeCap.ROUND)
            shader = sweepGradientShader
        } else {
            shader = null
            color = trackColor
        }
    }

    private var dashedTrackDrawable = DashedTrackDrawable(dashWidth, dashGap, startAngle, sweepAngle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerPosition.x = measuredWidth / 2f
        centerPosition.y = measuredHeight / 2f
        val largerThumbWidth = if (outerThumbRadius / 2f + outerThumbStrokeWidth / 2f >= innerThumbRadius / 2f + innerThumbStrokeWidth / 2f)
            (outerThumbRadius / 2f + outerThumbStrokeWidth / 2f)
        else
            (innerThumbRadius / 2f + innerThumbStrokeWidth / 2f)
        val seekBarMargin = if (largerThumbWidth >= barWidth / 2f) largerThumbWidth else barWidth / 2f
        radiusPx = (measuredWidth / 2f).coerceAtMost(measuredHeight / 2f) - seekBarMargin
    }

    override fun updateView() {
        trackPaint = Paint().apply {
            strokeWidth = barWidth
            style = Paint.Style.STROKE
            strokeCap = barStrokeCap.getPaintStrokeCap()
            isAntiAlias = true
            if (trackGradientColorsArray.size > 1) {
                val sweepGradientShader = createSweepGradient(barWidth, startAngle, dashedTrackDrawable.getPaintedSweepAngle(), trackGradientColorsArray, barStrokeCap == BarStrokeCap.ROUND)
                shader = sweepGradientShader
            } else {
                shader = null
                color = trackColor
            }
        }
        dashedTrackDrawable = DashedTrackDrawable(dashWidth, dashGap, startAngle, sweepAngle)
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
            dashedTrackDrawable.draw(it, rect, trackPaint)
        }
    }
}
