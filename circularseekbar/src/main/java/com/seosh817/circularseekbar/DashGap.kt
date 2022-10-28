package com.seosh817.circularseekbar

@JvmInline
value class DashGap private constructor(val value: Float) {

    init {
        require(value >= 0) { "dashGap $value must be more than 0" }
    }

    operator fun plus(dashWidth: DashWidth) = value + dashWidth.value

    fun isAvailable(): Boolean = value > 0

    companion object {
        fun from(value: Float): DashGap {
            return DashGap(value)
        }
    }
}
