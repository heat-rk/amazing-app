package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import ru.heatalways.amazingasfuckapplication.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.utils.StringResource

data class MenuItem(
    val id: String,
    val title: StringResource,
    val icon: PainterResource,
)
