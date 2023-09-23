@file:OptIn(ExperimentalFoundationApi::class)

package ru.heatalways.amazingasfuckapplication.core.composables.pager

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.common.utils.extract
import ru.heatalways.amazingasfuckapplication.common.utils.painterRes
import ru.heatalways.amazingasfuckapplication.common.utils.strRes
import ru.heatalways.amazingasfuckapplication.core.composables.pager.PagerContract.SideEffect
import ru.heatalways.amazingasfuckapplication.core.composables.pager.PagerContract.ViewState
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppBar
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppBarActionItem
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppSnackbarHost
import ru.heatalways.amazingasfuckapplication.core.design.composables.PagerScreenPaws
import ru.heatalways.amazingasfuckapplication.core.design.composables.TitleSubtitle
import ru.heatalways.amazingasfuckapplication.core.design.composables.shimmerEffect
import ru.heatalways.amazingasfuckapplication.core.design.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.core.design.styles.Insets
import ru.heatalways.amazingasfuckapplication.core.design.R as DesignR

@Composable
fun <T> PagerScreen(
    viewModel: PagerViewModel<T>,
    title: String,
    icon: Painter,
    content: @Composable (T) -> Unit,
    contentShimmer: @Composable () -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val sideEffects = viewModel.container.sideEffectFlow
    val snackbarHostState = remember { SnackbarHostState() }

    PagerScreenSideEffect(
        sideEffects = sideEffects,
        snackbarHostState = snackbarHostState
    )

    PagerScreen(
        title = title,
        icon = icon,
        state = state,
        snackbarHostState = snackbarHostState,
        onNavigationButtonClick = viewModel::onNavigateBack,
        onPageSelected = viewModel::onPageSelected,
        onReloadButtonClick = viewModel::onReloadButtonClick,
        onShareClick = viewModel::onShareClick,
        content = content,
        contentShimmer = contentShimmer,
    )
}

@Composable
private fun <T> PagerScreen(
    title: String,
    icon: Painter,
    state: ViewState<T>,
    snackbarHostState: SnackbarHostState,
    onNavigationButtonClick: () -> Unit,
    onPageSelected: (Int) -> Unit,
    onReloadButtonClick: () -> Unit,
    onShareClick: () -> Unit,
    content: @Composable (T) -> Unit,
    contentShimmer: @Composable () -> Unit,
) {
    val contentVerticalAlignmentBias = -0.5f

    Scaffold(
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AppBar(
                title = title,
                icon = icon,
                onGoBackClick = onNavigationButtonClick,
                actions = getAppBarActionItems(
                    state = state,
                    onShareClick = onShareClick
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        when (state) {
            is ViewState.Error -> {
                PagerScreenErrorState(
                    state = state,
                    onReloadButtonClick = onReloadButtonClick,
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
                    onPageSelected = onPageSelected,
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
    onPageSelected: (Int) -> Unit,
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
                onPageSelected(page)
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
    onReloadButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        TitleSubtitle(
            title = stringResource(DesignR.string.error_title),
            subtitle = state.message.extract() ?: "",
            modifier = Modifier
                .wrapContentSize()
                .align(
                    BiasAlignment(
                        horizontalBias = 0f,
                        verticalBias = contentVerticalAlignmentBias
                    )
                )
        )

        PagerScreenPaws(
            onClick = onReloadButtonClick,
            text = stringResource(DesignR.string.error_try_again),
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(bottom = Insets.ExtraLarge)
        )
    }
}

@Composable
private fun PagerScreenSideEffect(
    sideEffects: Flow<SideEffect>,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current

    LaunchedEffect(sideEffects, context) {
        sideEffects
            .onEach { sideEffect ->
                when (sideEffect) {
                    is SideEffect.Message -> {
                        handleMessageSideEffect(
                            sideEffect = sideEffect,
                            snackbarHostState = snackbarHostState,
                            context = context,
                        )
                    }
                }
            }
            .launchIn(this)
    }
}

private suspend fun handleMessageSideEffect(
    sideEffect: SideEffect.Message,
    snackbarHostState: SnackbarHostState,
    context: Context,
) {
    val message = sideEffect.message.extract(context)
        ?: return

    snackbarHostState.showSnackbar(message)
}

private fun <T> getAppBarActionItems(
    state: ViewState<T>,
    onShareClick: () -> Unit
) = when (state) {
    is ViewState.Ok<T> -> {
        listOf(
            AppBarActionItem(
                icon = painterRes(DesignR.drawable.icon_share),
                contentDescription = strRes(DesignR.string.share),
                onClick = onShareClick
            )
        )
    }

    else -> {
        emptyList()
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
            snackbarHostState = SnackbarHostState(),
            onPageSelected = {},
            onNavigationButtonClick = {},
            onReloadButtonClick = {},
            onShareClick = {},
            title = "Крутые факты",
            icon = painterResource(DesignR.drawable.icon_cat),
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