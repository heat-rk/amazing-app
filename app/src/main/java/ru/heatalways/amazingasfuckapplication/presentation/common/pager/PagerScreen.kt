package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.drawBackgroundLighting
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.radialBackgroundLighting
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.shimmerEffect
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
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
    val pawsColor = AppTheme.colors.primary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            AppTheme.colors.background,
                        )
                    )
                )
                .padding(Insets.Large)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(with(LocalDensity.current) { Sizes.PawsNextButton.toDp() })
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .clickable(
                        indication = rememberRipple(
                            color = pawsColor
                        ),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onIntent(Intent.ShowNext) }
                    )
                    .radialBackgroundLighting(pawsColor)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_paws),
                    colorFilter = ColorFilter.tint(pawsColor),
                    contentDescription = stringResource(R.string.go_next_icon_content_description),
                    modifier = Modifier
                        .width(Sizes.WantMoreIcon)
                )

                Spacer(modifier = Modifier.height(Insets.Small))

                Text(
                    text = stringResource(R.string.want_more),
                    color = pawsColor,
                    modifier = Modifier
                        .drawBackgroundLighting(pawsColor) { canvas, paint ->
                            val horizontalOffset = 10f
                            val verticalOffset = 3f

                            canvas.drawRect(
                                -horizontalOffset,
                                size.height / 2 - verticalOffset,
                                size.width + horizontalOffset,
                                size.height / 2 + verticalOffset,
                                paint
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun <T> PagerScreenLoadingState(
    contentPadding: PaddingValues,
    state: ViewState.Loading<T>,
    contentShimmer: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        contentShimmer()
    }
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
    val okState = ViewState.Ok(
        items = persistentListOf(
            "Какой-то факт #1",
            "Какой-то факт #2"
        )
    )

    val loadingState = ViewState.Loading<String>()

    AppTheme {
        PagerScreen(
            state = loadingState,
            onIntent = {},
            title = "Крутые факты",
            icon = painterResource(R.drawable.icon_cat),
            content = {
                Text(text = it)
            },
            contentShimmer = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(32.dp)
                            .clip(AppTheme.shapes.medium)
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.height(Insets.Default))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(32.dp)
                            .clip(AppTheme.shapes.medium)
                            .shimmerEffect()
                    )
                }
            }
        )
    }
}