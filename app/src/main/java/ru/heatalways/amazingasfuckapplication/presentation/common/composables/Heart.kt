package ru.heatalways.amazingasfuckapplication.presentation.common.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes

@Composable
fun Heart(
    color: Color = Color.Transparent,
    strokeColor: Color = AppTheme.colors.primary,
    strokeWidth: Dp = Sizes.HeartStrokeWidth,
    modifier: Modifier = Modifier,
) {
    val path = Path()

    Canvas(
        modifier = modifier
    ) {
        val strokeWidthPx = strokeWidth.toPx()

        path.apply {
            val width: Float = size.width
            val height: Float = size.height

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