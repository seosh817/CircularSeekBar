package com.seosh817.circularseekbar_example

import android.content.res.Resources

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density
