package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.SweepGradient
import android.view.View
import com.seosh817.circularseekbar.Constants.START_ANGLE_OFFSET
import com.seosh817.circularseekbar.utils.MathUtils

abstract class ProgressViewBase(context: Context): View(context) {

    abstract var centerPosition: PointF
    abstract var radiusPx: Float

    abstract fun updateView()

    fun createSweepGradient(barWidth: Float, startAngle: Float, sweepAngle: Float, gradientArray: IntArray, rounded: Boolean): SweepGradient {
        val shader = SweepGradient(centerPosition.x, centerPosition.y, gradientArray, getGradientPositions(sweepAngle, gradientArray))
        val gradientRotationMatrix = Matrix()
        if (rounded) {
            gradientRotationMatrix.preRotate(START_ANGLE_OFFSET + startAngle - MathUtils.radiansToDegrees((barWidth / 2) / radiusPx), centerPosition.x, centerPosition.y)
        } else {
            gradientRotationMatrix.preRotate(START_ANGLE_OFFSET + startAngle, centerPosition.x, centerPosition.y)
        }
        shader.setLocalMatrix(gradientRotationMatrix)
        return shader
    }

    private fun getGradientPositions(sweepAngle: Float, gradientArray: IntArray): FloatArray {
        if (gradientArray.size > 1) {
            val step = 1 / (gradientArray.size - 1f) * (sweepAngle / 360f)
            return FloatArray(gradientArray.size) {
                step * it
            }
        } else throw IllegalArgumentException(ILLEGAL_GRADIENT_ARRAY_SIZE_EXCEPTION_MESSAGE)
    }

    companion object {
        const val ILLEGAL_GRADIENT_ARRAY_SIZE_EXCEPTION_MESSAGE = "The size of gradientArray must be greater than 1."
    }
}