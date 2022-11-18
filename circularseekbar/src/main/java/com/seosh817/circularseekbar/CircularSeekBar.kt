package com.seosh817.circularseekbar

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.seosh817.circularseekbar.MathUtils.lerp
import com.seosh817.circularseekbar.MathUtils.radiansToDegrees
import kotlin.math.atan2

class CircularSeekBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    private val trackView: TrackView = TrackView(context)

    private val progressView: ProgressView = ProgressView(context)

    /** Previous value of [CircularSeekBar]. */
    private var previousProgress: Float = 0f

    /** Center (x, y) coordinates of Arc. */
    private var centerPosition: PointF = PointF()

    /** ValueAnimator of progress animation. */
    private var progressAnimator: ValueAnimator? = null

    /** [OnAnimationEndListener] is an interface for listening to the animation is the end. */
    private var onProgressChangedListener: OnProgressChangedListener? = null

    /** [OnProgressChangedListener] is an interface for listening to the progress is changed. */
    private var onAnimationEndListener: OnAnimationEndListener? = null

    /** Current value of [CircularSeekBar]. */
    var progress: Float = 0f
        set(value) {
            previousProgress = progress
            field = when {
                value >= max -> max
                value <= min -> min
                else -> value
            }
            if (showAnimation) {
                animateProgress()
            } else {
                progressView.progress = value
                onProgressChangedListener?.onProgressChanged(value)
            }
        }

    /** Minimum value of [CircularSeekBar]. */
    var min: Float = 0f
        set(value) {
            field = value
            progressView.min = value
        }

    /** Maximum value of [CircularSeekBar]. */
    var max: Float = 100f
        set(value) {
            field = value
            progressView.max = value
        }

    /** The Angle to start drawing [CircularSeekBar] from. */
    var startAngle: Float = 90f
        set(value) {
            field = value
            trackView.startAngle = value
            progressView.startAngle = value
        }

    /** The Angle through which to draw [CircularSeekBar] bar. */
    var sweepAngle: Float = 180f
        set(value) {
            field = value
            trackView.sweepAngle = value
            progressView.sweepAngle = value
        }

    /** Background track color of [CircularSeekBar]. */
    @ColorInt
    var trackColor: Int = Color.LTGRAY
        set(value) {
            field = value
            trackView.trackColor = value
        }

    /** Foreground progress color of [CircularSeekBar]. */
    @ColorInt
    var progressColor: Int = Color.parseColor("#FF189BFA")
        set(value) {
            field = value
            progressView.progressColor = value
        }

    /** The thickness of [CircularSeekBar]. */
    @Px
    var barWidth: Float = dp2Px(6).toFloat()
        set(value) {
            field = dp2Px(value)
            trackView.barWidth = dp2Px(value)
            progressView.barWidth = dp2Px(value)
            updateCircularSeekBar()
        }

    /** Styles to use for arcs endings. */
    var barStrokeCap: BarStrokeCap = BarStrokeCap.ROUND
        set(value) {
            field = value
            trackView.barStrokeCap = value
            progressView.barStrokeCap = value
        }

    /** Show animation when [CircularSeekBar]'s Progress changes. */
    var showAnimation: Boolean = true
        set(value) {
            field = value
            updateCircularSeekBar()
        }

    /** Animation of [CircularSeekBar]. */
    var circularSeekBarAnimation: CircularSeekBarAnimation = CircularSeekBarAnimation.BOUNCE
        set(value) {
            field = value
            updateCircularSeekBar()
        }

    /** Duration milliseconds of the animation. */
    var animationDurationMillis: Int = 1000
        set(value) {
            field = value
            updateCircularSeekBar()
        }

    /** Set to true if you want to interact with TapDown to change [CircularSeekBar]'s progress. */
    var interactive: Boolean = true
        set(value) {
            field = value
            progressView.interactive = value
            updateCircularSeekBar()
        }

    /** Dash width of seek bar. */
    var dashWidth: Float = 0f
        set(value) {
            field = value
            trackView.dashWidth = value
            progressView.dashWidth = value
            updateCircularSeekBar()
        }

    /** Dash gap of seek bar. */
    var dashGap: Float = 0f
        set(value) {
            field = value
            trackView.dashGap = value
            progressView.dashGap = value
            updateCircularSeekBar()
        }

    /** The radius of the [CircularSeekBar] inner thumb. */
    var innerThumbRadius: Float = 0f
        set(@Dp value) {
            field = dp2Px(value)
            trackView.innerThumbRadius = dp2Px(value)
            progressView.innerThumbRadius = dp2Px(value)
            updateCircularSeekBar()
        }

    /** The stroke width of the [CircularSeekBar] inner thumb. */
    var innerThumbStrokeWidth: Float = 0f
        set(@Dp value) {
            field = dp2Px(value)
            trackView.innerThumbStrokeWidth = dp2Px(value)
            progressView.innerThumbStrokeWidth = dp2Px(value)
            updateCircularSeekBar()
        }

    /** Color of the [CircularSeekBar] inner thumb. */
    var innerThumbColor: Int = Color.parseColor("#FF189BFA")
        set(value) {
            field = value
            progressView.innerThumbColor = value
        }

    /** Style of the [CircularSeekBar] of inner thumb. */
    var innerThumbStyle: ThumbStyle = ThumbStyle.FILL
        set(value) {
            field = value
            progressView.innerThumbStyle = value
        }

    /** The radius of the [CircularSeekBar] outer thumb. */
    var outerThumbRadius: Float = 0f
        set(@Dp value) {
            field = dp2Px(value)
            trackView.outerThumbRadius = dp2Px(value)
            progressView.outerThumbRadius = dp2Px(value)
            updateCircularSeekBar()
        }

    /** The stroke width of the [CircularSeekBar] outer thumb. */
    var outerThumbStrokeWidth: Float = 0f
        set(@Dp value) {
            field = dp2Px(value)
            trackView.outerThumbStrokeWidth = dp2Px(value)
            progressView.outerThumbStrokeWidth = dp2Px(value)
            updateCircularSeekBar()
        }

    /** Color of the [CircularSeekBar] outer thumb. */
    var outerThumbColor: Int = Color.WHITE
        set(value) {
            field = value
            progressView.outerThumbColor = value
        }

    /** Style of the [CircularSeekBar] of outer thumb. */
    var outerThumbStyle: ThumbStyle = ThumbStyle.FILL
        set(value) {
            field = value
            progressView.outerThumbStyle = value
        }

    /** Radius of [CircularSeekBar]. */
    private var radiusPx: Float = 0f
        set(value) {
            field = value
            trackView.radiusPx = value
            progressView.radiusPx = value
        }

    private val dashSum: DashSum
        get() = DashSum.of(dashWidth, dashGap)

    /** Foreground progressGradientColors of [CircularSeekBar]. */
    var progressGradientColorsArray: IntArray = intArrayOf()
        set(value) {
            field = value
            progressView.progressGradientColorsArray = value
        }

    /** Background trackGradientColors of [CircularSeekBar]. */
    var trackGradientColorsArray: IntArray = intArrayOf()
        set(value) {
            field = value
            trackView.trackGradientColorsArray = value
        }

    /** Interpolator of animation. */
    var animationInterpolator: Interpolator? = null

    /** The variable that [CircularSeekBar]'s Animation is ongoing or not. */
    var isAnimating: Boolean = true
        private set
    
    init {
        if (attributeSet != null) {
            getAttrs(attributeSet, defStyle)
        }
    }

    private fun getAttrs(attributeSet: AttributeSet?, defStyleAttrs: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircularSeekBar,
            defStyleAttrs,
            0
        )
        try {
            setTypedArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTypedArray(a: TypedArray) {
        this.progress = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_progress, progress)
        this.min = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_min, min)
        this.max = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_max, max)
        this.startAngle = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_startAngle, startAngle)
        this.sweepAngle = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_sweepAngle, sweepAngle)
        this.trackColor = a.getColor(R.styleable.CircularSeekBar_circularSeekBar_trackColor, trackColor)
        this.progressColor = a.getColor(R.styleable.CircularSeekBar_circularSeekBar_progressColor, progressColor)
        this.barWidth = a.getDimension(R.styleable.CircularSeekBar_circularSeekBar_barWidth, barWidth)
        this.barStrokeCap = a
            .getInt(R.styleable.CircularSeekBar_circularSeekBar_barStrokeCap, barStrokeCap.value)
            .let {
                BarStrokeCap
                    .fromValue(it)
            }
        this.showAnimation = a.getBoolean(R.styleable.CircularSeekBar_circularSeekBar_showAnimation, showAnimation)
        this.circularSeekBarAnimation = a
            .getInt(R.styleable.CircularSeekBar_circularSeekBar_animation, circularSeekBarAnimation.value)
            .let {
                CircularSeekBarAnimation
                    .fromValue(it)
            }
        this.animationDurationMillis = a.getInt(R.styleable.CircularSeekBar_circularSeekBar_animationDurationMillis, circularSeekBarAnimation.value)
        this.dashWidth = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_dashWidth, dashWidth)
        this.dashGap = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_dashGap, dashGap)
        this.interactive = a.getBoolean(R.styleable.CircularSeekBar_circularSeekBar_interactive, interactive)

        this.innerThumbRadius = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_innerThumbRadius, innerThumbRadius)
        this.innerThumbStrokeWidth = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_innerThumbStrokeWidth, innerThumbStrokeWidth)
        this.innerThumbColor = a.getInt(R.styleable.CircularSeekBar_circularSeekBar_innerThumbColor, innerThumbColor)
        this.innerThumbStyle = a
            .getInt(R.styleable.CircularSeekBar_circularSeekBar_outerThumbStyle, innerThumbStyle.value)
            .let {
                ThumbStyle.fromValue(it)
            }

        this.outerThumbRadius = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_outerThumbRadius, outerThumbRadius)
        this.outerThumbStrokeWidth = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_outerThumbStrokeWidth, outerThumbStrokeWidth)
        this.outerThumbColor = a.getInt(R.styleable.CircularSeekBar_circularSeekBar_outerThumbColor, outerThumbColor)
        this.outerThumbStyle = a
            .getInt(R.styleable.CircularSeekBar_circularSeekBar_outerThumbStyle, outerThumbStyle.value)
            .let {
                ThumbStyle.fromValue(it)
            }

        val progressGradientColorsResourceId = a.getResourceId(R.styleable.CircularSeekBar_circularSeekBar_progressGradientColors, 0)
        if (progressGradientColorsResourceId != 0) {
            val colorArray = resources.getIntArray(progressGradientColorsResourceId)
            progressGradientColorsArray = colorArray
        }

        val trackGradientColorsResourceId = a.getResourceId(R.styleable.CircularSeekBar_circularSeekBar_progressGradientColors, 0)
        if (trackGradientColorsResourceId != 0) {
            val colorArray = resources.getIntArray(progressGradientColorsResourceId)
            progressGradientColorsArray = colorArray
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addView(trackView)
        addView(progressView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredProperties()
    }

    private fun setMeasuredProperties() {
        centerPosition.x = measuredWidth / 2f
        centerPosition.y = measuredHeight / 2f
        val largerThumbWidth = if (outerThumbRadius / 2f + outerThumbStrokeWidth / 2f >= innerThumbRadius / 2f + innerThumbStrokeWidth / 2f)
            (outerThumbRadius / 2f + outerThumbStrokeWidth / 2f)
        else
            (innerThumbRadius / 2f + innerThumbStrokeWidth / 2f)
        val seekBarMargin = if (largerThumbWidth >= barWidth / 2f) largerThumbWidth else barWidth / 2f
        radiusPx = (measuredWidth / 2f).coerceAtMost(measuredHeight / 2f) - seekBarMargin
    }

    private fun updateCircularSeekBar() {
        post {
            updateTrackView()
            updateProgressView()
        }
    }

    private fun updateTrackView() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            this.width = this@CircularSeekBar.width
            this.height = this@CircularSeekBar.height
        }
        with(trackView) {
            layoutParams = params
        }
        removeView(trackView)
        addView(trackView)
    }

    private fun updateProgressView() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            this.width = this@CircularSeekBar.width
            this.height = this@CircularSeekBar.height
        }
        with(progressView) {
            layoutParams = params
            this.centerPosition = this@CircularSeekBar.centerPosition
            this.radiusPx = this@CircularSeekBar.radiusPx
        }
        removeView(progressView)
        addView(progressView)
    }

    private fun animateProgress() {
        progressAnimator?.cancel()
        ValueAnimator
            .ofFloat(0f, 1f)
            .apply {
                interpolator = if (this@CircularSeekBar.animationInterpolator != null) {
                    animationInterpolator
                } else {
                    circularSeekBarAnimation.getInterpolator()
                }
                duration = 1000L
                addUpdateListener {
                    val value = it.animatedValue as Float
                    val lerpValue = lerp(value, progress, previousProgress)
                    val animatedProgress = if (lerpValue < min) {
                        min
                    } else if (lerpValue > max) {
                        max
                    } else {
                        lerpValue
                    }
                    progressView.progress = animatedProgress
                    onProgressChangedListener?.onProgressChanged(animatedProgress)
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        isAnimating = true
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        onAnimationEndListener?.onAnimationEnd(progress)
                        progressAnimator = null
                        isAnimating = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        onAnimationEndListener?.onAnimationEnd(progress)
                        progressAnimator = null
                    }

                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }
            .also {
                it.start()
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (interactive) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    performClick()
                    handleTouchEvent(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    handleTouchEvent(event)
                }
                MotionEvent.ACTION_UP -> {
                    handleTouchEvent(event)
                }
            }
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    private fun handleTouchEvent(event: MotionEvent) {
        val relativeX = measuredWidth / 2f - event.x
        val relativeY = event.y - measuredHeight / 2f
        val angle = radiansToDegrees(atan2(relativeX, relativeY))
        val angleToProgress = angleToProgress(angle)
        if (angleToProgress >= 0) {
            progress = angleToProgress
        }
    }

    private fun angleToProgress(angle: Float): Float {
        val relativeAngle = if (angle - startAngle >= 0) {
            angle - startAngle
        } else {
            360 - startAngle + angle
        }

        if (dashSum.canDashed()) {
            for (i in 0 until dashSum.getTotalDashCounts(sweepAngle)) {
                val relativeDashStartAngle = dashSum * i
                val relativeDashEndAngle = (relativeDashStartAngle + dashWidth) % 360

                if (relativeAngle in relativeDashStartAngle..relativeDashEndAngle) {
                    val totalFullDashesRatio = (dashWidth * i) / dashSum.getTotalDashWidth(sweepAngle)
                    val halfWidthDashRatio = ((relativeAngle - dashSum * i) / dashWidth) / dashSum.getTotalDashCounts(sweepAngle)

                    return lerp(totalFullDashesRatio + halfWidthDashRatio, max, min)
                }
            }
        } else {
            return (relativeAngle / sweepAngle) * 100
        }
        return -1f
    }

    fun setOnProgressChangedListener(onProgressChangedListener: OnProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener
    }

    fun setOnAnimationEndListener(onAnimationEndListener: OnAnimationEndListener) {
        this.onAnimationEndListener = onAnimationEndListener
    }

    class Builder(context: Context) {
        private val circularSeekBar = CircularSeekBar(context)

        fun setProgress(value: Float) = circularSeekBar.apply {
            this.progress = value
        }

        fun setMin(value: Float) = circularSeekBar.apply {
            this.min = value
        }

        fun setMax(value: Float) = circularSeekBar.apply {
            this.max = value
        }

        fun setStartAngle(value: Float) = circularSeekBar.apply {
            this.startAngle = value
        }

        fun setSweepAngle(value: Float) = circularSeekBar.apply {
            this.sweepAngle = value
        }

        fun setBarWidth(@Px value: Float) = circularSeekBar.apply {
            this.barWidth = value
        }

        fun setTrackColor(@ColorInt value: Int) = circularSeekBar.apply {
            this.trackView.trackColor = value
        }

        fun setProgressColor(@ColorInt value: Int) = circularSeekBar.apply {
            this.progressView.progressColor = value
        }

        fun setInteractive(value: Boolean) = circularSeekBar.apply {
            this.interactive = value
        }

        fun setDashWidth(value: Float) = circularSeekBar.apply {
            this.dashWidth = value
        }

        fun setDashGap(value: Float) = circularSeekBar.apply {
            this.dashGap = value
        }

        fun setCircularSeekBarAnimation(value: CircularSeekBarAnimation) = circularSeekBar.apply {
            this.circularSeekBarAnimation = value
        }

        fun setCircularSeekBarAnimationInterpolator(value: Interpolator) = circularSeekBar.apply {
            this.animationInterpolator = value
        }

        fun setCircularSeekBarDuration(value: Int) = circularSeekBar.apply {
            this.animationDurationMillis = value
        }

        fun setInnerThumbRadius(value: Float) = circularSeekBar.apply {
            this.innerThumbRadius = value
        }

        fun setInnerThumbStrokeWidth(value: Float) = circularSeekBar.apply {
            this.innerThumbStrokeWidth = value
        }

        fun setInnerThumbColor(@ColorInt value: Int) = circularSeekBar.apply {
            this.innerThumbColor = value
        }

        fun setInnerThumbStyle(value: ThumbStyle) = circularSeekBar.apply {
            this.innerThumbStyle = value
        }

        fun setOuterThumbRadius(value: Float) = circularSeekBar.apply {
            this.outerThumbRadius = value
        }

        fun setOuterThumbStrokeWidth(value: Float) = circularSeekBar.apply {
            this.outerThumbStrokeWidth = value
        }

        fun setOuterThumbColor(@ColorInt value: Int) = circularSeekBar.apply {
            this.outerThumbColor = value
        }

        fun setOuterThumbStyle(value: ThumbStyle) = circularSeekBar.apply {
            this.outerThumbStyle = value
        }

        fun setTrackGradientColors(value: IntArray) = circularSeekBar.apply {
            this.trackGradientColorsArray = value
        }

        fun setProgressGradientColors(value: IntArray) = circularSeekBar.apply {
            this.progressGradientColorsArray = value
        }

        fun setOnProgressChangedListener(onProgressChangedListener: OnProgressChangedListener) = circularSeekBar.apply {
            this.onProgressChangedListener = onProgressChangedListener
        }

        fun setOnAnimationEndListener(onAnimationEndListener: OnAnimationEndListener) = circularSeekBar.apply {
            this.onAnimationEndListener = onAnimationEndListener
        }

        fun build(): CircularSeekBar = circularSeekBar
    }
}
