package ru.heatalways.amazingapplication.core.design.composables

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.heatalways.amazingapplication.core.design.styles.AppTheme

@Composable
fun AppSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        modifier = modifier,
    )
}

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = { data -> AppSnackbar(snackbarData = data) },
        modifier = modifier,
    )
}