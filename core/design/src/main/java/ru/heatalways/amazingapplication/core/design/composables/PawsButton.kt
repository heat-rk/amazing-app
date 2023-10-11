package ru.heatalways.amazingapplication.core.design.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.heatalways.amazingapplication.core.design.R
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets
import ru.heatalways.amazingapplication.core.design.styles.Sizes
import ru.heatalways.amazingapplication.core.design.R as DesignR

@Composable
fun PagerScreenPaws(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    pawsColor: Color = AppTheme.colors.primary,
    disabledPawsColor: Color = pawsColor.copy(alpha = 0.3f),
    isEnabled: Boolean = true,
) {
    val color = if (isEnabled) pawsColor else disabledPawsColor

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(Sizes.WantMoreIcon)
                .clip(CircleShape)
                .conditional(isEnabled) { radialBackgroundLighting(color) }
                .clickable(
                    enabled = isEnabled,
                    indication = rememberRipple(
                        color = color
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick
                )
        ) {
            Image(
                painter = painterResource(DesignR.drawable.icon_paws),
                colorFilter = ColorFilter.tint(color),
                contentDescription = stringResource(R.string.go_next_icon_content_description),
                modifier = Modifier
                    .padding(Insets.Default)
            )
        }

        Spacer(modifier = Modifier.height(Insets.Small))

        Text(
            text = text,
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .rectangularBackgroundLighting(color)
        )
    }
}

@Composable
@Preview
private fun PagerScreenPawsPreview() {
    PagerScreenPaws(
        onClick = {},
        isEnabled = false,
        text = "Тык на лапку",
        modifier = Modifier
            .padding(bottom = Insets.ExtraLarge)
    )
}