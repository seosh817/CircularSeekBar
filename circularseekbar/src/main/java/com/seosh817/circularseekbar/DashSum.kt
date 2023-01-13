package com.seosh817.circularseekbar

import com.seosh817.circularseekbar.utils.MathUtils

class DashSum(
    val dashWidth: DashWidth,
    val dashGap: DashGap
) {

    private val dashSum: Float
        get() = dashWidth + dashGap

    operator fun times(count: Int) = dashSum * count

    fun canDashed(): Boolean = dashWidth.isAvailable() && dashGap.isAvailable()

    fun getTotalDashCounts(sweepAngle: Float): Int {
        if (sweepAngle <= 30.0) return 0
        if (sweepAngle > 0 && sweepAngle <= dashWidth.value) return 1
        return if (sweepAngle >= (sweepAngle / dashSum).toInt() * dashSum + dashWidth.value) {
            (sweepAngle / dashSum).toInt() + 1
        } else {
            (sweepAngle / dashSum).toInt()
        }
    }

    fun getFullDashesRatio(sweepAngle: Float, min: Float, max: Float, progress: Float): Float = if (getTotalDashCounts(sweepAngle) <= 0) {
        0f
    } else {
        getProgressDashCounts(sweepAngle, min, max, progress) / getTotalDashCounts(sweepAngle).toFloat()
    }

    fun getProgressDashCounts(sweepAngle: Float, min: Float, max: Float, progress: Float): Int {
        return (getTotalDashCounts(sweepAngle) * MathUtils.lerpRatio(min, max, progress)).toInt()
    }

    fun getTotalDashWidth(sweepAngle: Float): Float {
        return dashWidth * getTotalDashCounts(sweepAngle)
    }

    fun getTotalDashSum(sweepAngle: Float): Float {
        return dashSum * getTotalDashCounts(sweepAngle)
    }

    /** Calculate the relative angle of full dashes in [CircularSeekBar]. */
    fun getFullDashesProgressAngle(sweepAngle: Float, min: Float, max: Float, progress: Float): Float {
        return if (getTotalDashCounts(sweepAngle) >= getProgressDashCounts(sweepAngle, min, max, progress) + 1) {
            dashSum * getProgressDashCounts(sweepAngle, min, max, progress)
        } else {
            dashSum * (getProgressDashCounts(sweepAngle, min, max, progress) - 1) + dashWidth.value
        }
    }

    companion object {
        fun of(dashWidth: Float, dashGap: Float): DashSum {
            return DashSum(DashWidth.from(dashWidth), DashGap.from(dashGap))
        }
    }
}
