package ru.heatalways.amazingapplication.core.design.styles

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val materialColors: ColorScheme,
    val gold: Color = Color(0xFFFFC800),
    val silver: Color = Color(0xFFD8D8D8),
    val bronze: Color = Color(0xFF703D00),
) {
    val primary = materialColors.primary
    val onPrimary = materialColors.onPrimary
    val primaryContainer = materialColors.primaryContainer
    val onPrimaryContainer = materialColors.onPrimaryContainer
    val secondary = materialColors.secondary
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
        primaryContainer = Color(0x4DBD5CBD),
        onPrimaryContainer = Color(0xFF000000),
        secondary = Color(0xFF56FF30),
        onSecondary = Color(0xFF072900),
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