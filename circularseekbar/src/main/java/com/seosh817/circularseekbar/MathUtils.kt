package com.seosh817.circularseekbar

object MathUtils {

    /** Convert degrees to radians */
    @JvmStatic
    fun degreesToRadians(angle: Double): Double = angle * Math.PI / 180.0

    /** Convert radians to degrees */
    @JvmStatic
    fun radiansToDegrees(radians: Double): Double = radians * 180.0 / Math.PI

    /** Convert degrees to radians */
    @JvmStatic
    fun degreesToRadians(angle: Float): Float = (angle * Math.PI / 180.0).toFloat()

    /** Convert radians to degrees */
    @JvmStatic
    fun radiansToDegrees(radians: Float): Float = (radians * 180.0 / Math.PI).toFloat()

    /** Computes the linear interpolation between from and to. calculate a + t(b - a). */
    fun lerp(ratio: Double, to: Float, from: Float = 0f): Double {
        return from + (to - from) * ratio
    }

    /** Computes the linear interpolation between from and to. calculate a + t(b - a). */
    fun lerp(ratio: Float, to: Float, from: Float = 0f): Float {
        return from + (to - from) * ratio
    }

    /** Calculate linear interpolator percentage. */
    fun lerpRatio(from: Float, to: Float, progress: Float): Float {
        return (progress / (from + to))
    }
}