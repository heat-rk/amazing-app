package ru.heatalways.amazingasfuckapplication.feature.pidors.compose_impl.ui

import ru.heatalways.amazingasfuckapplication.common.utils.PainterResource

data class PidorItem(
    val id: Long,
    val name: String,
    val avatar: PainterResource,
    val isSelected: Boolean = false,
    val tapCount: Int = 0,
)