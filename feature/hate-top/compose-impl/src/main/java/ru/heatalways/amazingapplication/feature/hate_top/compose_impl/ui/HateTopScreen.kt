@file:OptIn(ExperimentalFoundationApi::class)

package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.heatalways.amazingapplication.common.utils.extract
import ru.heatalways.amazingapplication.common.utils.painterRes
import ru.heatalways.amazingapplication.common.utils.strRes
import ru.heatalways.amazingapplication.core.design.composables.AppBar
import ru.heatalways.amazingapplication.core.design.composables.AppBarActionItem
import ru.heatalways.amazingapplication.core.design.composables.AppOutlinedCard
import ru.heatalways.amazingapplication.core.design.composables.PagerScreenPaws
import ru.heatalways.amazingapplication.core.design.composables.shimmerEffect
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets
import ru.heatalways.amazingapplication.core.design.styles.Sizes
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.R
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di.HateTopComponent
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopContract.ViewState
import ru.heatalways.amazingapplication.core.design.R as DesignR

@Composable
fun HateTopScreen(
    viewModel: HateTopViewModel = viewModel(factory = HateTopComponent.hateTopViewModelFactory),
    title: String,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    HateTopScreen(
        title = title,
        state = state,
        onNavigationButtonClick = viewModel::onNavigationButtonClick,
        onCreateClick = viewModel::onCreateClick,
        onEditClick = viewModel::onEditClick,
        onDeleteClick = viewModel::onDeleteClick,
        onItemClick = viewModel::onItemClick,
        onItemLongClick = viewModel::onItemLongClick,
        onReloadClick = viewModel::onReloadClick,
    )
}

@Composable
private fun HateTopScreen(
    state: ViewState,
    onCreateClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onItemClick: (HateTopItem) -> Unit,
    onItemLongClick: (HateTopItem) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onReloadClick: () -> Unit,
    title: String,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = title,
                icon = painterResource(DesignR.drawable.icon_leaderboard),
                actions = getAppBarActions(
                    state = state,
                    onAddClick = onCreateClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                ),
                onGoBackClick = onNavigationButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = ViewState::key,
            label = "HateTopScreenContentAnimation",
            transitionSpec = {
                fadeIn() + slideInVertically(
                    animationSpec = tween(400),
                    initialOffsetY = { fullHeight -> fullHeight }
                ) togetherWith fadeOut(animationSpec = tween(200))
            }
        ) { state ->
            when (state) {
                ViewState.Loading -> {
                    HateTopLoadingScreen(
                        modifier = Modifier.padding(contentPadding),
                    )
                }
                is ViewState.Ok -> {
                    HateTopOkScreen(
                        state = state,
                        onCreateClick = onCreateClick,
                        onItemClick = onItemClick,
                        onItemLongClick = onItemLongClick,
                        modifier = Modifier.padding(contentPadding),
                    )
                }
                is ViewState.Error -> {
                    HateTopErrorScreen(
                        state = state,
                        onReloadClick = onReloadClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun HateTopLoadingScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(Insets.Default),
        userScrollEnabled = false,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        repeat(10) {
            item {
                HateTopItemShimmer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )

                Spacer(modifier = Modifier.height(Insets.Medium))
            }
        }
    }
}

@Composable
private fun HateTopErrorScreen(
    state: ViewState.Error,
    onReloadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = state.message.extract() ?: "",
            textAlign = TextAlign.Center,
            color = AppTheme.colors.primary,
            modifier = Modifier
                .wrapContentSize()
        )

        PagerScreenPaws(
            onClick = onReloadClick,
            text = stringResource(DesignR.string.error_try_again),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Insets.ExtraLarge)
        )
    }
}

@Composable
private fun HateTopOkScreen(
    state: ViewState.Ok,
    onCreateClick: () -> Unit,
    onItemClick: (HateTopItem) -> Unit,
    onItemLongClick: (HateTopItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.items.isNotEmpty()) {
        HateTopItems(
            items = state.items,
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.hate_top_empty_list_hint),
                textAlign = TextAlign.Center,
                color = AppTheme.colors.primary,
                modifier = Modifier
                    .wrapContentSize()
            )

            PagerScreenPaws(
                onClick = onCreateClick,
                text = stringResource(R.string.add_hate_top_unit),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Insets.ExtraLarge)
            )
        }
    }
}

@Composable
private fun HateTopItems(
    items: ImmutableList<HateTopItem>,
    onItemClick: (HateTopItem) -> Unit,
    onItemLongClick: (HateTopItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(Insets.Default),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.id }
        ) { index, item ->
            HateTopItem(
                position = index,
                item = item,
                onClick = { onItemClick(item) },
                onLongClick = { onItemLongClick(item) },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateItemPlacement()
            )

            if (index != items.lastIndex) {
                Spacer(modifier = Modifier.height(Insets.Medium))
            }
        }
    }
}

@Composable
private fun HateTopItem(
    position: Int,
    item: HateTopItem,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = when (position) {
        0 -> AppTheme.colors.gold
        1 -> AppTheme.colors.silver
        2 -> AppTheme.colors.bronze
        else -> AppTheme.colors.primary
    }

    AppOutlinedCard(
        onClick = onClick,
        color = color,
        containerColor = if (item.isSelected) {
            color.copy(alpha = 0.2f)
        } else {
            Color.Transparent
        },
        onLongClick = onLongClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = item.avatar.extract(),
                contentDescription = null,
                modifier = Modifier
                    .size(Sizes.HateTopUnitAvatarSize)
            )

            Spacer(modifier = Modifier.height(Insets.Default))

            Column(
                modifier = Modifier
                    .padding(Insets.Small)
            ) {
                Text(
                    text = item.name,
                    color = color,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.bodyLarge,
                    modifier = Modifier
                        .wrapContentSize()
                )

                Text(
                    text = pluralStringResource(R.plurals.hate_top_tap_count_description, item.tapCount, item.tapCount),
                    color = color,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.bodyLarge,
                    modifier = Modifier
                        .wrapContentSize()
                )
            }

            Spacer(modifier = Modifier.height(Insets.Default))
        }
    }
}

@Composable
private fun HateTopItemShimmer(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(Sizes.HateTopUnitAvatarSize)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )

        val textLineHeight: Dp

        with(LocalDensity.current) {
            textLineHeight = AppTheme.typography.bodyLarge.fontSize.toDp()
        }

        Box(
            modifier = Modifier
                .padding(Insets.Small)
                .height(textLineHeight)
                .fillMaxWidth(0.6f)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )
    }
}

private fun getAppBarAddAction(
    onClick: () -> Unit
) = AppBarActionItem(
    icon = painterRes(DesignR.drawable.icon_add),
    contentDescription = strRes(R.string.add_hate_top_unit),
    onClick = onClick
)

private fun getAppBarDeleteAction(
    onClick: () -> Unit
) = AppBarActionItem(
    icon = painterRes(DesignR.drawable.icon_delete),
    contentDescription = strRes(R.string.delete_hate_top_unit),
    onClick = onClick
)

private fun getAppBarEditAction(
    onClick: () -> Unit
) = AppBarActionItem(
    icon = painterRes(DesignR.drawable.icon_edit),
    contentDescription = strRes(R.string.edit_hate_top_unit),
    onClick = onClick
)

private fun getAppBarActions(
    state: ViewState,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
): List<AppBarActionItem> =
    when (state) {
        is ViewState.Ok -> {
            val selected = state.items.filter { it.isSelected }

            when (selected.size) {
                0 -> {
                    listOf(
                        getAppBarAddAction(onAddClick),
                    )
                }

                1 -> {
                    listOf(
                        getAppBarDeleteAction(onDeleteClick),
                        getAppBarEditAction(onEditClick),
                    )
                }

                else -> {
                    listOf(
                        getAppBarDeleteAction(onDeleteClick),
                    )
                }
            }
        }
        else -> emptyList()
    }

@Composable
@Preview
private fun HateTopScreenPreview() {
    val okState = ViewState.Ok(
        items = persistentListOf(
            HateTopItem(
                id = 1,
                name = "№1 Моргенштерн",
                avatar = painterRes(DesignR.drawable.icon_cat),
                isSelected = true,
                tapCount = 6,
            ),
            HateTopItem(
                id = 2,
                name = "№2 Милохин большое название прям очень большое большое название прям очень большое большое название прям очень большое большое название прям очень большое",
                avatar = painterRes(DesignR.drawable.icon_boobs),
            )
        ),
    )

    val emptyState = ViewState.Ok(items = persistentListOf())

    val loadingState = ViewState.Loading

    val errorState = ViewState.Error(strRes("Что-то пошло не так"))

    AppTheme {
        HateTopScreen(
            title = "Хейт-топ",
            state = okState,
            onNavigationButtonClick = {},
            onCreateClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onItemClick = {},
            onItemLongClick = {},
            onReloadClick = {},
        )
    }
}