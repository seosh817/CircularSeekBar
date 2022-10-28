package com.seosh817.circularseekbar

class DashSum(
    val dashWidth: DashWidth,
    val dashGap: DashGap
) {

    private val dashSum: Float
        get() = dashWidth + dashGap

    operator fun times(count: Int) = dashSum * count

    fun canDashed(): Boolean = dashWidth.isAvailable() && dashGap.isAvailable()

    fun getFullProgressRatio(sweepAngle: Float, min: Float, max: Float, progress: Float): Float {
        return getProgressDashCounts(sweepAngle, min, max, progress) / getTotalDashCounts(sweepAngle).toFloat()
    }

    fun getProgressDashCounts(sweepAngle: Float, min: Float, max: Float, progress: Float): Int {
        return (getTotalDashCounts(sweepAngle) * MathUtils.lerpRatio(min, max, progress)).toInt()
    }

    fun getTotalDashCounts(sweepAngle: Float): Int {
        return if (sweepAngle >= (sweepAngle / dashSum) * dashSum + dashWidth.value) {
            (sweepAngle / dashSum).toInt() + 1
        } else {
            (sweepAngle / dashSum).toInt()
        }
    }

    companion object {
        fun of(dashWidth: Float, dashGap: Float): DashSum {
            return DashSum(DashWidth.from(dashWidth), DashGap.from(dashGap))
        }
    }
}
