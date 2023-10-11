package ru.heatalways.amazingapplication.core.design.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets

@Composable
fun TitleSubtitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colors.primary,
        )

        Spacer(modifier = Modifier.height(Insets.Small))

        Text(
            text = subtitle,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.primary,
        )
    }
}

@Composable
private fun TitleSubtitlePreview() {

}