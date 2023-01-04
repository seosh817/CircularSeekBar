package com.seosh817.circularseekbar_example

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View

/** px size to dp size. */
val Float.dp: Float
    get() = this / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

/** dp size to px size. */
val Float.px: Float
    get() = this * Resources.getSystem().displayMetrics.density
