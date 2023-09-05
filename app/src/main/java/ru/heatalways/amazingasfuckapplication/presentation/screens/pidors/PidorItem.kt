package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import ru.heatalways.amazingasfuckapplication.utils.PainterResource

data class PidorItem(
    val id: Long,
    val name: String,
    val avatar: PainterResource,
    val isSelected: Boolean = false,
    val tapCount: Int = 0,
)