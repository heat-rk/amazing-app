package ru.heatalways.amazingasfuckapplication.presentation.common.composables

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes

@Composable
fun Heart(
    modifier: Modifier = Modifier,
    color: Color = Color.Transparent,
    strokeColor: Color = AppTheme.colors.primary,
    strokeWidth: Dp = Sizes.HeartStrokeWidth,
    blurRadius: Float = 30f,
) {
    val path = Path()
    val lightingPaint = Paint()
    val frameworkPaint = lightingPaint.asFrameworkPaint()
    frameworkPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
    frameworkPaint.color = strokeColor.toArgb()
    frameworkPaint.style = android.graphics.Paint.Style.STROKE
    frameworkPaint.strokeWidth = with(LocalDensity.current) { strokeWidth.toPx() }

    Canvas(
        modifier = modifier
    ) {
        drawHeart(
            x = 0f,
            y = 0f,
            width = size.width,
            height = size.height,
            color = color,
            strokeColor = strokeColor,
            strokeWidth = strokeWidth,
            path = path,
            lightingPaint = lightingPaint,
        )
    }
}

fun DrawScope.drawHeart(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    rotation: Float = 0f,
    color: Color = Color.Transparent,
    strokeColor: Color = Color.Red,
    strokeWidth: Dp = Sizes.HeartStrokeWidth,
    path: Path,
    lightingPaint: Paint,
) {
    val strokeWidthPx = strokeWidth.toPx()

    path.apply {
        reset()

        // Starting point
        moveTo(width / 2, height / 5)

        // Upper left path
        cubicTo(
            5 * width / 14, 0f,
            0f, 0f,
            width / 28, 2 * height / 5
        )

        // Lower left path
        cubicTo(
            width / 14, 2 * height / 3,
            3 * width / 7, 5 * height / 6,
            width / 2, height - strokeWidthPx
        )

        // Lower right path
        cubicTo(
            4 * width / 7, 5 * height / 6,
            13 * width / 14, 2 * height / 3,
            27 * width / 28, 2 * height / 5
        )

        // Upper right path
        cubicTo(
            width, 0f,
            9 * width / 14, 0f,
            width / 2, height / 5
        )
    }

    rotate(
        degrees = rotation,
        pivot = Offset(x + width / 2, y + height / 2)
    ) {
        translate(
            left = x,
            top = y,
        ) {
            drawPath(
                path = path,
                color = color,
                style = Fill
            )

            drawPath(
                path = path,
                color = strokeColor,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round,
                )
            )

            drawIntoCanvas { canvas ->
                canvas.drawPath(
                    path = path,
                    paint = lightingPaint,
                )
            }
        }
    }
}

@Composable
@Preview
private fun HeartPreview() {
    AppTheme {
        Heart(
            modifier = Modifier
                .size(100.dp)
        )
    }
}