package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.Px

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

    /** Dash width of [CircularSeekBar]. */
    var dashWidth: Float by progressViewProperty(0f)

    /** Dash gap of [CircularSeekBar]. */
    var dashGap: Float by progressViewProperty(0f)

    /** The radius of the [CircularSeekBar] inner thumb. */
    var innerThumbRadius: Float by progressViewProperty(0f)

    /** The stroke width of the [CircularSeekBar] inner thumb. */
    var innerThumbStrokeWidth: Float by progressViewProperty(0f)

    /** Color of the [CircularSeekBar] inner thumb. */
    var innerThumbColor: Int by progressViewProperty(Color.WHITE)

    /** The radius of the [CircularSeekBar] outer thumb. */
    var outerThumbRadius: Float by progressViewProperty(0f)

    /** The stroke width of the [CircularSeekBar] outer thumb. */
    var outerThumbStrokeWidth: Float by progressViewProperty(0f)

    /** The stroke width of the [CircularSeekBar] outer thumb. */
    var outerThumbColor: Int by progressViewProperty(Color.parseColor("#FF189BFA"))

    /** Set to true if you want to interact with TapDown to change [CircularSeekBar]'s progress. */
    var interactive: Boolean by progressViewProperty(true)

    /** DashedProgressDrawable of [CircularSeekBar]. */
    private var dashedProgressDrawable = DashedProgressDrawable(dashWidth, dashGap, min, max, progress, startAngle, sweepAngle)

    /** ThumbDrawable of [CircularSeekBar]. */
    private var thumbDrawable = ThumbDrawable(centerPosition, radiusPx, min, max, progress, startAngle, sweepAngle, dashWidth, dashGap, innerThumbRadius, outerThumbRadius)

    private var progressPaint = Paint().apply {
        color = progressColor
        strokeWidth = barWidth
        style = Paint.Style.STROKE
        strokeCap = barStrokeCap.getPaintStrokeCap()
        isAntiAlias = true
    }

    private var innerCirclePaint = Paint().apply {
        color = innerThumbColor
        strokeWidth = innerThumbStrokeWidth
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private var outerCirclePaint = Paint().apply {
        color = innerThumbColor
        strokeWidth = innerThumbStrokeWidth
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
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

        innerCirclePaint = Paint().apply {
            color = innerThumbColor
            strokeWidth = innerThumbStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }

        outerCirclePaint = Paint().apply {
            color = innerThumbColor
            strokeWidth = innerThumbStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }


        dashedProgressDrawable = DashedProgressDrawable(dashWidth, dashGap, min, max, progress, startAngle, sweepAngle)
        thumbDrawable = ThumbDrawable(centerPosition, radiusPx, min, max, progress, startAngle, sweepAngle, dashWidth, dashGap, innerThumbRadius, outerThumbRadius)
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
            dashedProgressDrawable.draw(it, rect, progressPaint)
            thumbDrawable.draw(canvas, innerCirclePaint, outerCirclePaint)
        }
    }
}