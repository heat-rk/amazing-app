package ru.heatalways.amazingasfuckapplication.utils

import android.net.Uri
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
    data class ByUri(val uri: Uri) : PainterResource
}

fun painterRes(@DrawableRes res: Int) = PainterResource.ByRes(res)

fun painterRes(painter: Painter) = PainterResource.ByPainter(painter)

fun painterRes(path: String) = PainterResource.ByPath(path)

fun painterRes(uri: Uri) = PainterResource.ByUri(uri)

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
    is PainterResource.ByUri -> {
        val context = LocalContext.current
        val imageLoader = context.imageLoader

        rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(uri)
                .apply { size(Size.ORIGINAL) }
                .build(),
            imageLoader = imageLoader,
        )
    }
}

fun PainterResource.isEmpty() = when (this) {
    is PainterResource.ByPainter -> true
    is PainterResource.ByPath -> path.isBlank()
    is PainterResource.ByRes -> res > 0
    is PainterResource.ByUri -> uri.path?.isBlank() ?: true
}

fun PainterResource.isNotEmpty() = !isEmpty()