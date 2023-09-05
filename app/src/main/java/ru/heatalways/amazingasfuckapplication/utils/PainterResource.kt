package ru.heatalways.amazingasfuckapplication.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Size
import java.io.File

@Immutable
sealed interface PainterResource {
    data class ByRes(@DrawableRes val res: Int) : PainterResource
    data class ByPainter(val painter: Painter) : PainterResource
    data class ByPath(val path: String) : PainterResource
}

fun painterRes(@DrawableRes res: Int) = PainterResource.ByRes(res)

fun painterRes(painter: Painter) = PainterResource.ByPainter(painter)

fun painterRes(path: String) = PainterResource.ByPath(path)

@Composable
fun PainterResource.extract() = when (this) {
    is PainterResource.ByPainter -> painter
    is PainterResource.ByRes -> painterResource(id = res)
    is PainterResource.ByPath -> {
        val context = LocalContext.current
        val imageLoader = context.imageLoader

        rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(File(path))
                .apply { size(Size.ORIGINAL) }
                .build(),
            imageLoader = imageLoader,
        )
    }
}