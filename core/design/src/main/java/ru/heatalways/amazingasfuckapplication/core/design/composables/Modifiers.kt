package ru.heatalways.amazingasfuckapplication.core.design.composables

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.heatalways.amazingasfuckapplication.core.design.styles.AppTheme

private const val SHIMMER_ANIM_LABEL = "shimmer"
private const val SHIMMER_REPEAT_DELAY = 1200

@Composable
fun Modifier.shimmerEffect(
    shimmerBackgroundColor: Color = AppTheme.colors.primaryContainer,
    shimmerForegroundColor: Color = AppTheme.colors.primary,
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var position by remember { mutableStateOf(Offset.Zero) }
    val transition = rememberInfiniteTransition(label = SHIMMER_ANIM_LABEL)

    val configuration = LocalConfiguration.current

    val windowWidth: Float

    with(LocalDensity.current) {
        windowWidth = configuration.screenWidthDp.dp.toPx()
    }

    val startOffsetX by transition.animateFloat(
        label = SHIMMER_ANIM_LABEL,
        initialValue = -position.x - windowWidth,
        targetValue = windowWidth * 2 - position.x,
        animationSpec = infiniteRepeatable(
            animation = tween(SHIMMER_REPEAT_DELAY)
        ),
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                shimmerBackgroundColor,
                shimmerForegroundColor,
                shimmerBackgroundColor,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
        position = it.positionInWindow()
    }
}

fun Modifier.radialBackgroundLighting(
    color: Color,
) = background(
    brush = Brush.radialGradient(
        0f to color.copy(alpha = 0.3f),
        1f to Color.Transparent
    )
)

fun Modifier.rectangularBackgroundLighting(
    color: Color,
    horizontalOffset: Float = 10f,
    verticalOffset: Float = 3f,
) = drawBackgroundLighting(color) { canvas, paint ->
    canvas.drawRect(
        -horizontalOffset,
        size.height / 2 - verticalOffset,
        size.width + horizontalOffset,
        size.height / 2 + verticalOffset,
        paint
    )
}

fun Modifier.drawBackgroundLighting(
    lightingColor: Color,
    blurRadius: Float = 30f,
    style: android.graphics.Paint.Style = android.graphics.Paint.Style.FILL,
    strokeWidth: Float = 0f,
    block: DrawScope.(Canvas, Paint) -> Unit
): Modifier {
    val paint = Paint()
    val frameworkPaint = paint.asFrameworkPaint()
    frameworkPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
    frameworkPaint.style = style
    frameworkPaint.color = lightingColor.toArgb()
    frameworkPaint.strokeWidth = strokeWidth

    return drawBehind {
        drawIntoCanvas { canvas ->
            block(canvas, paint)
        }
    }
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}