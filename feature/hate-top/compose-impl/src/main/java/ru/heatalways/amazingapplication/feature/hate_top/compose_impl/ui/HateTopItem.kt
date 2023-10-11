package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui

import ru.heatalways.amazingapplication.common.utils.PainterResource

data class HateTopItem(
    val id: Long,
    val name: String,
    val avatar: PainterResource,
    val isSelected: Boolean = false,
    val tapCount: Int = 0,
)