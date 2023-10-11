package ru.heatalways.amazingapplication.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

inline fun <reified T> Any.ifInstance(block: (T) -> Unit) =
    (this as? T)?.let(block)

fun Offset.coerceIn(rect: Rect) = coerceIn(
    minimumX = rect.left,
    minimumY = rect.top,
    maximumX = rect.right,
    maximumY = rect.bottom,
)

fun Offset.coerceIn(
    minimumX: Float,
    minimumY: Float,
    maximumX: Float,
    maximumY: Float
): Offset {
    val newX = x.coerceIn(minimumX, maximumX)
    val newY = y.coerceIn(minimumY, maximumY)

    return if (x != newX || y != newY) {
        Offset(newX, newY)
    } else {
        this
    }
}

val Size.aspectRatio get() = width / height

fun Size.roundToIntSize() = IntSize(width.roundToInt(), height.roundToInt())

fun Offset.roundToIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

operator fun Offset.plus(size: Size) = Offset(x + size.width, y + size.height)