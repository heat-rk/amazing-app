package ru.heatalways.amazingapplication.feature.menu.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.collections.immutable.toImmutableList
import ru.heatalways.amazingapplication.common.utils.extract
import ru.heatalways.amazingapplication.core.design.composables.AppOutlinedCard
import ru.heatalways.amazingapplication.core.design.composables.radialBackgroundLighting
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets
import ru.heatalways.amazingapplication.core.design.styles.Sizes
import ru.heatalways.amazingapplication.feature.menu.impl.di.MenuComponent
import ru.heatalways.amazingapplication.feature.menu.impl.ui.MenuContract.ViewState

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = viewModel(
        factory = MenuComponent.menuViewModelFactory
    )
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    MenuScreen(
        state = state,
        onMenuItemClick = viewModel::onMenuItemClick
    )
}

@Composable
private fun MenuScreen(
    state: ViewState,
    onMenuItemClick: (MenuItem) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(Insets.Default),
        horizontalArrangement = Arrangement.spacedBy(Insets.Default),
        contentPadding = PaddingValues(Insets.Default),
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(
            items = state.items,
            span = null,
            key = { _, item -> item.id },
        ) { index, item ->
            val color = if (index % 4 == 0 || (index + 1) % 4 == 0) {
                AppTheme.colors.primary
            } else {
                AppTheme.colors.secondary
            }

            AppOutlinedCard(
                onClick = { onMenuItemClick(item) },
                color = color,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Sizes.MenuItemHeight)
                    .radialBackgroundLighting(color)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Insets.Small)
                ) {
                    Image(
                        painter = item.icon.extract(),
                        colorFilter = ColorFilter.tint(color),
                        contentDescription = null,
                        modifier = Modifier
                            .height(Sizes.MenuIconHeight)
                    )

                    Text(
                        text = item.title.extract() ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun MenuScreenPreview() {
    AppTheme {
        MenuScreen(
            state = ViewState(
                items = MenuItem.values().toList().toImmutableList()
            ),
            onMenuItemClick = {}
        )
    }
}