package ru.heatalways.amazingasfuckapplication.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

sealed interface PainterResource {
    data class ByRes(@DrawableRes val res: Int) : PainterResource
    data class ByPainter(val painter: Painter) : PainterResource
}

fun painterRes(@DrawableRes res: Int) = PainterResource.ByRes(res)

fun painterRes(painter: Painter) = PainterResource.ByPainter(painter)

@Composable
fun PainterResource.extract() = when (this) {
    is PainterResource.ByPainter -> painter
    is PainterResource.ByRes -> painterResource(id = res)
}