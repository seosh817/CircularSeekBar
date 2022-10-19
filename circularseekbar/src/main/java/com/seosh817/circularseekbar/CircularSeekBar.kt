package com.seosh817.circularseekbar

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.animation.Interpolator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.seosh817.circularseekbar.MathUtils.lerp

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

    /** Radius of [CircularSeekBar]. */
    private var radiusPx: Float = 0f

    /** ValueAnimator of progress animation. */
    private var progressAnimator: ValueAnimator? = null

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
            }
        }

    /** Minimum value of [CircularSeekBar]. */
    var min: Float = 0f
        set(value) {
            field = value
            progressView.min = value
            updateCircularSeekBar()
        }

    /** Maximum value of [CircularSeekBar]. */
    var max: Float = 100f
        set(value) {
            field = value
            progressView.max = value
            updateCircularSeekBar()
        }

    /** The Angle to start drawing [CircularSeekBar] from. */
    var startAngle: Float = 0f
        set(value) {
            field = value
            trackView.startAngle = value
            progressView.startAngle = value
            updateCircularSeekBar()
        }

    /** The Angle through which to draw [CircularSeekBar] bar. */
    var sweepAngle: Float = 0f
        set(value) {
            field = value
            trackView.sweepAngle = value
            progressView.sweepAngle = value
            updateCircularSeekBar()
        }

    /** The thickness of [CircularSeekBar]. */
    @Px
    var barWidth: Float = dp2Px(8).toFloat()
        set(value) {
            field = value
            trackView.barWidth = value
            progressView.barWidth = value
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
        this.barWidth = a.getDimension(R.styleable.CircularSeekBar_circularSeekBar_barWidth, barWidth)
        this.interactive = a.getBoolean(R.styleable.CircularSeekBar_circularSeekBar_interactive, interactive)
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

        with(trackView) {
            this.trackColor = a.getColor(R.styleable.CircularSeekBar_circularSeekBar_trackColor, trackColor)
            this.startAngle = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_startAngle, startAngle)
            this.sweepAngle = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_sweepAngle, sweepAngle)
            this.barWidth = a.getDimension(R.styleable.CircularSeekBar_circularSeekBar_barWidth, barWidth)
        }

        with(progressView) {
            this.progress = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_progress, progress)
            this.progressColor = a.getColor(R.styleable.CircularSeekBar_circularSeekBar_progressColor, progressColor)
            this.startAngle = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_startAngle, startAngle)
            this.sweepAngle = a.getFloat(R.styleable.CircularSeekBar_circularSeekBar_sweepAngle, sweepAngle)
            this.barWidth = a.getDimension(R.styleable.CircularSeekBar_circularSeekBar_barWidth, barWidth)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredProperties()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        updateProgressView()
    }

    private fun setMeasuredProperties() {
        centerPosition.x = measuredWidth / 2f
        centerPosition.y = measuredHeight / 2f
        radiusPx = (measuredWidth / 2f).coerceAtMost(measuredHeight / 2f) - barWidth / 2f
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
            this.centerPosition = this@CircularSeekBar.centerPosition
            this.radiusPx = this@CircularSeekBar.radiusPx
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
                    progressView.progress = lerp(value, progress, previousProgress)
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        isAnimating = true
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        progressAnimator = null
                        isAnimating = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        progressAnimator = null
                    }

                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }
            .also {
                it.start()
            }
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

        fun setCircularSeekBarAnimation(value: CircularSeekBarAnimation) = circularSeekBar.apply {
            this.circularSeekBarAnimation = value
        }

        fun setCircularSeekBarAnimationInterpolator(value: Interpolator) = circularSeekBar.apply {
            this.animationInterpolator = value
        }

        fun setCircularSeekBarDuration(value: Int) = circularSeekBar.apply {
            this.animationDurationMillis = value
        }

        fun build(): CircularSeekBar = circularSeekBar
    }

    companion object {
        val TAG: String = CircularSeekBar::class.java.simpleName
        const val START_ANGLE_OFFSET = 90
    }
}
