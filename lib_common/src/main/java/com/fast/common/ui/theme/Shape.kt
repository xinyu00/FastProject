package com.fast.common.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes

import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val BottomSheetShape = RoundedCornerShape(
    topStart = CornerSize(16.dp),
    topEnd = CornerSize(16.dp),
    bottomEnd = CornerSize(0.dp),
    bottomStart = CornerSize(0.dp)
)
