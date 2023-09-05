package ru.heatalways.amazingasfuckapplication.presentation.common.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = AppTheme.shapes.medium,
        modifier = modifier
            .drawBackgroundLighting(
                lightingColor = AppTheme.colors.primary
            ) { canvas, paint ->
                canvas.drawRoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    radiusX = 12.dp.toPx(),
                    radiusY = 12.dp.toPx(),
                    paint = paint,
                )
            }
    ) {
        Text(
            text = text
        )
    }
}

@Composable
@Preview
private fun AppButtonPreview() {
    AppTheme {
        AppButton(
            text = "Нажми меня",
            onClick = { /* ... */ },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
    }
}