package ru.heatalways.amazingasfuckapplication.presentation.common.composables

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
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes

@Composable
fun PagerScreenPaws(
    onClick: () -> Unit,
    text: String,
    pawsColor: Color = AppTheme.colors.primary,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(Sizes.WantMoreIcon)
                .clip(CircleShape)
                .radialBackgroundLighting(pawsColor)
                .clickable(
                    indication = rememberRipple(
                        color = pawsColor
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick
                )
        ) {
            Image(
                painter = painterResource(R.drawable.icon_paws),
                colorFilter = ColorFilter.tint(pawsColor),
                contentDescription = stringResource(R.string.go_next_icon_content_description),
                modifier = Modifier
                    .padding(Insets.Default)
            )
        }

        Spacer(modifier = Modifier.height(Insets.Small))

        Text(
            text = text,
            color = pawsColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .rectangularBackgroundLighting(pawsColor)
        )
    }
}