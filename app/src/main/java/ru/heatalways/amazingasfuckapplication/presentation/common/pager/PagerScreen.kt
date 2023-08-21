package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Elevations
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes

@Composable
fun <T> PagerScreen(
    viewModel: PagerViewModel<T>,
    title: String,
    icon: Painter,
    content: @Composable (T) -> Unit,
    contentShimmer: @Composable () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PagerScreen(
        title = title,
        icon = icon,
        state = state,
        onIntent = viewModel::intent,
        content = content,
        contentShimmer = contentShimmer,
    )
}

@Composable
private fun <T> PagerScreen(
    title: String,
    icon: Painter,
    state: ViewState<T>,
    onIntent: (Intent) -> Unit,
    content: @Composable (T) -> Unit,
    contentShimmer: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = title,
                icon = icon,
                onGoBackClick = { onIntent(Intent.GoBack) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        when (state) {
            is ViewState.Error -> {
                PagerScreenErrorState(
                    state = state,
                    onIntent = onIntent,
                    contentPadding = contentPadding,
                )
            }
            is ViewState.Loading -> {
                PagerScreenLoadingState(
                    state = state,
                    onIntent = onIntent,
                    contentShimmer = contentShimmer,
                    contentPadding = contentPadding,
                )
            }
            is ViewState.Ok -> {
                PagerScreenOkState(
                    state = state,
                    onIntent = onIntent,
                    content = content,
                    contentPadding = contentPadding,
                )
            }
        }
    }
}

@Composable
fun <T> PagerScreenOkState(
    contentPadding: PaddingValues,
    state: ViewState.Ok<T>,
    onIntent: (Intent) -> Unit,
    content: @Composable (T) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .clickable { onIntent(Intent.ShowNext) }
                .background(Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        AppTheme.colors.background,
                    )
                ))
        ) {
            Spacer(modifier = Modifier.height(Insets.Large))

            Image(
                painter = painterResource(R.drawable.icon_paws),
                colorFilter = ColorFilter.tint(AppTheme.colors.primary),
                contentDescription = stringResource(R.string.go_next_icon_content_description),
                modifier = Modifier
                    .width(Sizes.WantMoreIcon)
                    .shadow(
                        elevation = Elevations.PawsShadow,
                        ambientColor = AppTheme.colors.primary,
                        spotColor = AppTheme.colors.primary,
                        shape = CircleShape,
                        clip = false,
                    )
            )

            Spacer(modifier = Modifier.height(Insets.Default))

            Text(
                text = stringResource(R.string.want_more),
                color = AppTheme.colors.primary,
            )

            Spacer(modifier = Modifier.height(Insets.Large))
        }
    }
}

@Composable
fun <T> PagerScreenLoadingState(
    contentPadding: PaddingValues,
    state: ViewState.Loading<T>,
    onIntent: (Intent) -> Unit,
    contentShimmer: @Composable () -> Unit,
) {

}

@Composable
fun <T> PagerScreenErrorState(
    contentPadding: PaddingValues,
    state: ViewState.Error<T>,
    onIntent: (Intent) -> Unit,
) {

}

@Composable
@Preview
private fun PagerScreenPreview() {
    AppTheme {
        PagerScreen(
            state = ViewState.Ok(
                items = persistentListOf(
                    "Какой-то факт #1",
                    "Какой-то факт #2"
                )
            ),
            onIntent = {},
            title = "Крутые факты",
            icon = painterResource(R.drawable.icon_cat),
            content = {
                Text(text = it)
            },
            contentShimmer = {

            }
        )
    }
}