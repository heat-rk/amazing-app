package ru.heatalways.amazingapplication.core.composables.image_cropper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

internal sealed interface ImageCroppedBounds {
    object NotCalculated : ImageCroppedBounds

    data class Calculated(
        val offset: Offset,
        val size: Size
    ) : ImageCroppedBounds
}
