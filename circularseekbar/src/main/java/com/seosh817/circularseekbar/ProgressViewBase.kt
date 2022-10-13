package com.seosh817.circularseekbar

import android.content.Context
import android.graphics.PointF
import android.view.View

abstract class ProgressViewBase(context: Context): View(context) {

    abstract var centerPosition: PointF
    abstract var radiusPx: Float

    abstract fun updateView()
}