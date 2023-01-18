package com.seosh817.circularseekbar

@JvmInline
value class DashWidth private constructor(val value: Float) {

    init {
        require(value >= 0) { "dashWidth $value must be more than 0" }
    }

    operator fun plus(dashGap: DashGap) = dashGap.value + value

    operator fun times(count: Int) = value * count

    fun isAvailable(): Boolean = value > 0

    companion object {
        fun from(value: Float): DashWidth {
            return DashWidth(value)
        }
    }
}
