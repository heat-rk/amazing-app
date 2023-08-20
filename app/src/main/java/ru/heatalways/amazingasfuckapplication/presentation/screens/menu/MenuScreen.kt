package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.AppOutlinedCard
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes
import ru.heatalways.amazingasfuckapplication.utils.extract

const val MenuScreenRoute = "menu"

@Composable
fun MenuScreen(viewModel: MenuViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(Insets.Default),
        horizontalArrangement = Arrangement.spacedBy(Insets.Default),
        contentPadding = PaddingValues(Insets.Default),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            items = state.items,
            span = null,
            key = { item -> item.id },
        ) { item ->
            AppOutlinedCard(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Sizes.MenuItemHeight)
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
                        colorFilter = ColorFilter.tint(AppTheme.colors.primary),
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