package ru.heatalways.amazingasfuckapplication.presentation.styles

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val materialColors: ColorScheme
) {
    val primary = materialColors.primary
    val onPrimary = materialColors.onPrimary
    val primaryContainer = materialColors.primaryContainer
    val onPrimaryContainer = materialColors.onPrimaryContainer
    val secondary = materialColors.onSecondary
    val onSecondary = materialColors.onSecondary
    val secondaryContainer = materialColors.secondaryContainer
    val onSecondaryContainer = materialColors.onSecondaryContainer
    val background = materialColors.background
    val onBackground = materialColors.onBackground
    val surface = materialColors.surface
    val onSurface = materialColors.onSurface
    val surfaceVariant = materialColors.surfaceVariant
    val onSurfaceVariant = materialColors.onSurfaceVariant
    val error = materialColors.error
    val onError = materialColors.onError
}

val appColors = AppColors(
    materialColors = darkColorScheme(
        primary = Color(0xFFF600FF),
        onPrimary = Color(0xFF330435),
        primaryContainer = Color(0xFFBD5CBD),
        onPrimaryContainer = Color(0xFF000000),
        secondary = Color(0xFF6200FF),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFF9C5EFF),
        onSecondaryContainer = Color(0xFF000000),
        error = Color(0xFFBA1A1A),
        errorContainer = Color(0xFFFFDAD6),
        onError = Color(0xFFFFFFFF),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFF1A1A1A),
        onBackground = Color(0xFFD5D5D5),
        surface = Color(0xFFBD5CBD),
        onSurface = Color(0xFF000000),
        surfaceVariant = Color(0xFF1649FF),
        onSurfaceVariant = Color(0xFFFFFFFF),
        scrim = Color(0xFF1A1A1A),
    )
)

val LocalAppColors = staticCompositionLocalOf { appColors }