@file:OptIn(ExperimentalMaterial3Api::class)

package ru.heatalways.amazingasfuckapplication.presentation.common.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes

@Composable
fun AppOutlinedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary,
        ),
        border = BorderStroke(
            width = Sizes.MenuItemBorderWidth,
            color = AppTheme.colors.primary,
        ),
        modifier = modifier,
        onClick = onClick,
        content = content,
    )
}