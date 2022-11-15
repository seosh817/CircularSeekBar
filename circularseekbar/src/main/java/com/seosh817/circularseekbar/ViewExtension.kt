package com.seosh817.circularseekbar

import android.view.View

internal fun View.dp2Px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).toInt()
}

internal fun View.dp2Px(dp: Float): Float {
    val scale = resources.displayMetrics.density
    return dp * scale
}
