package com.seosh817.circularseekbar

import androidx.annotation.Dimension

@Retention(AnnotationRetention.BINARY)
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.FIELD,
  AnnotationTarget.LOCAL_VARIABLE
)
@Dimension(unit = Dimension.DP)
internal annotation class Dp
