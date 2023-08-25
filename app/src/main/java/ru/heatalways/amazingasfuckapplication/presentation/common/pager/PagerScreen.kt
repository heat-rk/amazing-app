@file:OptIn(ExperimentalFoundationApi::class)

package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.TitleSubtitle
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.radialBackgroundLighting
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.rectangularBackgroundLighting
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.shimmerEffect
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes
import ru.heatalways.amazingasfuckapplication.utils.extract
import ru.heatalways.amazingasfuckapplication.utils.strRes

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
    val contentVerticalAlignmentBias = -0.5f

    Scaffold(
        topBar = {
            AppBar(
                title = title,
                icon = icon,
                onGoBackClick = { onIntent(Intent.OnNavigationButtonClick) },
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
                    contentVerticalAlignmentBias = contentVerticalAlignmentBias,
                )
            }
            is ViewState.Loading -> {
                PagerScreenLoadingState(
                    state = state,
                    contentShimmer = contentShimmer,
                    contentPadding = contentPadding,
                    contentVerticalAlignmentBias = contentVerticalAlignmentBias,
                )
            }
            is ViewState.Ok -> {
                PagerScreenOkState(
                    state = state,
                    onIntent = onIntent,
                    content = content,
                    contentPadding = contentPadding,
                    contentVerticalAlignmentBias = contentVerticalAlignmentBias,
                )
            }
        }
    }
}

@Composable
private fun <T> PagerScreenOkState(
    contentPadding: PaddingValues,
    contentVerticalAlignmentBias: Float,
    state: ViewState.Ok<T>,
    onIntent: (Intent) -> Unit,
    content: @Composable (T) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        val pagerState = rememberPagerState { state.items.size }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                onIntent(Intent.OnPageSelected(page))
            }
        }

        HorizontalPager(
            state = pagerState,
            verticalAlignment = BiasAlignment.Vertical(contentVerticalAlignmentBias),
            modifier = Modifier
                .fillMaxSize()
        ) { index ->
            content(state.items[index])
        }

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
                            AppTheme.colors.background,
                            AppTheme.colors.background,
                        ),
                    )
                )
        ) {
            val coroutineScope = rememberCoroutineScope()

            PagerScreenPaws(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                text = stringResource(R.string.want_more),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Insets.ExtraLarge)
            )
        }
    }
}

@Composable
private fun <T> PagerScreenLoadingState(
    contentPadding: PaddingValues,
    contentVerticalAlignmentBias: Float,
    state: ViewState.Loading<T>,
    contentShimmer: @Composable () -> Unit,
) {
    Box(
        contentAlignment = BiasAlignment(
            horizontalBias = 0f,
            verticalBias = contentVerticalAlignmentBias
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        contentShimmer()
    }
}

@Composable
private fun <T> PagerScreenErrorState(
    contentPadding: PaddingValues,
    contentVerticalAlignmentBias: Float,
    state: ViewState.Error<T>,
    onIntent: (Intent) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        TitleSubtitle(
            title = stringResource(R.string.error_title),
            subtitle = state.message.extract() ?: "",
            modifier = Modifier
                .wrapContentSize()
                .align(BiasAlignment(
                    horizontalBias = 0f,
                    verticalBias = contentVerticalAlignmentBias
                ))
        )

        PagerScreenPaws(
            onClick = { onIntent(Intent.OnReloadButtonClick) },
            text = stringResource(R.string.error_try_again),
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(bottom = Insets.ExtraLarge)
        )
    }
}

@Composable
private fun PagerScreenPaws(
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

    val errorState = ViewState.Error<String>(
        strRes("Что-то пошло не так :(")
    )

    AppTheme {
        PagerScreen(
            state = okState,
            onIntent = {},
            title = "Крутые факты",
            icon = painterResource(R.drawable.icon_cat),
            content = {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
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