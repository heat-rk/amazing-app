package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppOutlinedCard
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.PagerScreenPaws
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.shimmerEffect
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes
import ru.heatalways.amazingasfuckapplication.utils.extract
import ru.heatalways.amazingasfuckapplication.utils.painterRes

object PidorsScreenRouteDefinition : ScreenRouteDefinition()

@Composable
fun PidorsScreen(viewModel: PidorsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PidorsScreen(
        state = state,
        onIntent = viewModel::intent
    )
}

@Composable
private fun PidorsScreen(
    state: ViewState,
    onIntent: (Intent) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.menu_item_pidors_list),
                icon = painterResource(R.drawable.icon_leaderboard),
                onGoBackClick = { onIntent(Intent.OnNavigationButtonClick) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        when (state) {
            ViewState.Loading -> {
                PidorsLoadingScreen(
                    modifier = Modifier.padding(contentPadding),
                )
            }
            is ViewState.Ok -> {
                PidorsOkScreen(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier.padding(contentPadding),
                )
            }
        }
    }
}

@Composable
private fun PidorsLoadingScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(Insets.Default),
        userScrollEnabled = false,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        repeat(3) {
            item {
                PidorItemShimmer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )

                Spacer(modifier = Modifier.height(Insets.Large))
            }
        }
    }
}

@Composable
private fun PidorsOkScreen(
    state: ViewState.Ok,
    modifier: Modifier = Modifier,
    onIntent: (Intent) -> Unit,
) {
    if (state.items.isNotEmpty()) {
        PidorItems(
            items = state.items,
            onIntent = onIntent,
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
                text = stringResource(R.string.pidors_empty_list_hint),
                textAlign = TextAlign.Center,
                color = AppTheme.colors.primary,
                modifier = Modifier
                    .wrapContentSize()
            )

            PagerScreenPaws(
                onClick = { onIntent(Intent.OnCreateClick) },
                text = stringResource(R.string.add_pidor),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Insets.ExtraLarge)
            )
        }
    }
}

@Composable
private fun PidorItems(
    items: ImmutableList<PidorItem>,
    onIntent: (Intent) -> Unit,
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
            PidorItem(
                position = index,
                item = item,
                onClick = { onIntent(Intent.OnItemClick(item)) },
                onLongClick = { onIntent(Intent.OnItemLongClick(item)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(Insets.Large))
        }
    }
}

@Composable
private fun PidorItem(
    position: Int,
    item: PidorItem,
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
        Image(
            painter = item.avatar.extract(),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = Insets.Large)
                .fillMaxWidth()
                .height(Sizes.PidorAvatarHeight)
        )

        Spacer(modifier = Modifier.height(Insets.Default))

        Text(
            text = item.name,
            textAlign = TextAlign.Center,
            color = color,
            style = AppTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Insets.Default))
    }
}

@Composable
private fun PidorItemShimmer(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = Insets.Large)
                .fillMaxWidth()
                .height(Sizes.PidorAvatarHeight)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(Insets.Default))

        val textLineHeight: Dp

        with(LocalDensity.current) {
            textLineHeight = AppTheme.typography.bodyLarge.fontSize.toDp()
        }

        Box(
            modifier = Modifier
                .height(textLineHeight)
                .fillMaxWidth(0.6f)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )
    }
}

@Composable
@Preview
private fun PidorsScreenPreview() {
    val okState = ViewState.Ok(
        items = persistentListOf(
            PidorItem(
                id = 1,
                name = "№1 Леня",
                avatar = painterRes(R.drawable.icon_cat),
                isSelected = true,
            ),
            PidorItem(
                id = 2,
                name = "№2 Лера",
                avatar = painterRes(R.drawable.icon_boobs),
            )
        ),
    )

    val emptyState = ViewState.Ok(items = persistentListOf())

    val loadingState = ViewState.Loading

    AppTheme {
        PidorsScreen(
            state = okState,
            onIntent = { /* ... */ },
        )
    }
}