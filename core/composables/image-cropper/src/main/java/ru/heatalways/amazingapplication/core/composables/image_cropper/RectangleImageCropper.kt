@file:OptIn(FlowPreview::class)

package ru.heatalways.amazingapplication.core.composables.image_cropper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.heatalways.amazingapplication.common.utils.aspectRatio
import ru.heatalways.amazingapplication.common.utils.coerceIn
import ru.heatalways.amazingapplication.common.utils.plus
import ru.heatalways.amazingapplication.common.utils.roundToIntOffset
import ru.heatalways.amazingapplication.common.utils.roundToIntSize
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import kotlin.math.min
import ru.heatalways.amazingapplication.core.design.R as DesignR

private const val CROPPING_BOX_PADDING = 50f
private const val CROP_CHANGING_FLOW_DEBOUNCE = 100L
private const val MIN_SCALE = 1f

@Composable
fun RectangleImageCropper(
    painter: Painter,
    modifier: Modifier = Modifier,
    maxScale: Float = 5f,
    croppingAspectRatio: Float = 1f,
    croppingBoxStrokeColor: Color = AppTheme.colors.primary,
    croppingBoxStrokeWidth: Dp = 1.dp,
    croppingBoxRadius: Dp = 6.dp,
    onCropChanged: (offset: IntOffset, size: IntSize) -> Unit,
) {
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val currentDensity = LocalDensity.current

    val croppingBoxStrokeWidthPx = remember {
        with(currentDensity) { croppingBoxStrokeWidth.toPx() }
    }

    val croppingBoxRadiusPx = remember {
        with(currentDensity) { croppingBoxRadius.toPx() }
    }

    val croppingBoxSize by remember {
        derivedStateOf {
            val width: Float
            val height: Float

            val minBoxDimension = min(canvasSize.width, canvasSize.height)

            if (croppingAspectRatio > 1f) {
                width = minBoxDimension - CROPPING_BOX_PADDING * 2
                height = width / croppingAspectRatio
            } else {
                height = minBoxDimension - CROPPING_BOX_PADDING * 2
                width = height * croppingAspectRatio
            }

            Size(width.coerceAtLeast(0f), height.coerceAtLeast(0f))
        }
    }

    val croppingBoxOffset by remember {
        derivedStateOf {
            val offsetX = (canvasSize.width - croppingBoxSize.width) / 2
            val offsetY = (canvasSize.height - croppingBoxSize.height) / 2
            Offset(offsetX, offsetY)
        }
    }

    val imageInitialSize by remember {
        derivedStateOf {
            val width: Float
            val height: Float

            if (painter.intrinsicSize.isSpecified) {
                val aspectRatio = painter.intrinsicSize.aspectRatio

                if (painter.intrinsicSize.width > painter.intrinsicSize.height) {
                    height = croppingBoxSize.height
                    width = height * aspectRatio
                } else {
                    width = croppingBoxSize.width
                    height = width / aspectRatio
                }
            } else {
                width = 0f
                height = 0f
            }

            Size(width.coerceAtLeast(0f), height.coerceAtLeast(0f))
        }
    }

    var scale by remember { mutableFloatStateOf(1f) }

    val imageSize by remember {
        derivedStateOf {
            imageInitialSize * scale
        }
    }

    var croppingBoxTranslation by remember {
        mutableStateOf(
            Offset(
                x = 0f,
                y = 0f,
            )
        )
    }

    val croppingBoxTranslationBounds by remember {
        derivedStateOf {
            Rect(
                left = 0f,
                top = 0f,
                right = imageSize.width - croppingBoxSize.width,
                bottom = imageSize.height - croppingBoxSize.height,
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val cropChangingFlow = remember {
        Channel<Unit>(Channel.BUFFERED)
    }

    LaunchedEffect(cropChangingFlow, coroutineScope) {
        cropChangingFlow.receiveAsFlow()
            .debounce(CROP_CHANGING_FLOW_DEBOUNCE)
            .filter { painter.intrinsicSize.isSpecified }
            .onEach {
                val painterSize = painter.intrinsicSize

                val offset = Offset(
                    x = croppingBoxTranslation.x / imageSize.width * painterSize.width,
                    y = croppingBoxTranslation.y / imageSize.height * painterSize.height,
                )

                val size = Size(
                    width = croppingBoxSize.width / imageSize.width * painterSize.width,
                    height = croppingBoxSize.height / imageSize.height * painterSize.height,
                )

                onCropChanged(
                    offset.roundToIntOffset(),
                    size.roundToIntSize(),
                )
            }
            .launchIn(coroutineScope)
    }

    LaunchedEffect(
        imageSize,
        croppingBoxTranslation,
        croppingBoxSize,
        painter.intrinsicSize,
        coroutineScope,
        cropChangingFlow
    ) {
        coroutineScope.launch { cropChangingFlow.send(Unit) }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clip(RectangleShape)
            .onGloballyPositioned { coordinates ->
                canvasSize = coordinates.size
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    val oldScale = scale

                    scale = (scale * zoom).coerceIn(
                        minimumValue = MIN_SCALE,
                        maximumValue = maxScale
                    )

                    val scaleOffset =
                        (croppingBoxTranslation + croppingBoxSize / 2f) * (scale / oldScale - 1)

                    croppingBoxTranslation = (croppingBoxTranslation - pan + scaleOffset)
                        .coerceIn(croppingBoxTranslationBounds)
                }
            }
    ) {
        with(painter) {
            translate(
                left = -croppingBoxTranslation.x + croppingBoxOffset.x,
                top = -croppingBoxTranslation.y + croppingBoxOffset.y,
            ) {
                draw(
                    size = imageSize
                )
            }
        }

        drawRoundRect(
            color = croppingBoxStrokeColor,
            style = Stroke(
                width = croppingBoxStrokeWidthPx
            ),
            cornerRadius = CornerRadius(
                x = croppingBoxRadiusPx,
                y = croppingBoxRadiusPx,
            ),
            topLeft = croppingBoxOffset,
            size = croppingBoxSize,
        )
    }
}

@Composable
@Preview
private fun RectangleImageCropperPreview() {
    AppTheme {
        RectangleImageCropper(
            painter = painterResource(DesignR.drawable.icon_leaderboard),
            onCropChanged = { _, _ ->  },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}