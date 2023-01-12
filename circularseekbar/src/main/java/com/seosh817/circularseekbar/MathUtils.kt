package com.seosh817.circularseekbar

object MathUtils {

    /** Convert degrees to radians */
    @JvmStatic
    fun degreesToRadians(angle: Float): Float = (angle * Math.PI / 180.0).toFloat()

    /** Convert radians to degrees */
    @JvmStatic
    fun radiansToDegrees(radians: Float): Float = (radians * 180.0 / Math.PI).toFloat()

    /** Computes the linear interpolation between from and to. calculate a + t(b - a). */
    @JvmStatic
    fun lerp(ratio: Float, to: Float, from: Float = 0f): Float {
        return from + (to - from) * ratio
    }

    /** Calculate linear interpolator percentage. */
    @JvmStatic
    fun lerpRatio(from: Float, to: Float, progress: Float): Float {
        return (progress / (from + to))
    }
}