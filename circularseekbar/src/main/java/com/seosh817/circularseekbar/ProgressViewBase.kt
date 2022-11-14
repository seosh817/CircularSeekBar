package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.SweepGradient
import android.view.View
import com.seosh817.circularseekbar.Constants.START_ANGLE_OFFSET

abstract class ProgressViewBase(context: Context): View(context) {

    abstract var centerPosition: PointF
    abstract var radiusPx: Float

    abstract fun updateView()

    fun createSweepGradient(startAngle: Float, sweepAngle: Float, gradientArray: IntArray): SweepGradient {
        val shader = SweepGradient(centerPosition.x, centerPosition.y, gradientArray, getGradientPositions(sweepAngle, gradientArray))
        val gradientRotationMatrix = Matrix()
        gradientRotationMatrix.preRotate(START_ANGLE_OFFSET + startAngle - 5, centerPosition.x, centerPosition.y)
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