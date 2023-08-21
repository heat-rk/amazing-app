package ru.heatalways.amazingasfuckapplication.presentation.common.composables

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme

private const val SHIMMER_ANIM_LABEL = "shimmer"
private const val SHIMMER_REPEAT_DELAY = 1000

@Composable
fun Modifier.shimmerEffect(
    shimmerBackgroundColor: Color = AppTheme.colors.primaryContainer,
    shimmerForegroundColor: Color = AppTheme.colors.primary,
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = SHIMMER_ANIM_LABEL)

    val startOffsetX by transition.animateFloat(
        label = SHIMMER_ANIM_LABEL,
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(SHIMMER_REPEAT_DELAY)
        ),
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

fun Modifier.radialBackgroundLighting(
    color: Color
) = background(
    brush = Brush.radialGradient(
        0f to color.copy(alpha = 0.3f),
        1f to Color.Transparent
    )
)

fun Modifier.drawBackgroundLighting(
    lightingColor: Color,
    blurRadius: Float = 30f,
    block: DrawScope.(Canvas, Paint) -> Unit
): Modifier {
    val paint = Paint()
    val frameworkPaint = paint.asFrameworkPaint()
    frameworkPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
    frameworkPaint.color = lightingColor.toArgb()

    return drawBehind {
        drawIntoCanvas { canvas ->
            block(canvas, paint)
        }
    }
}