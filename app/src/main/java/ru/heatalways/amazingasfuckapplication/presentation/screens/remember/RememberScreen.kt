package ru.heatalways.amazingasfuckapplication.presentation.screens.remember

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets

object RememberScreen {
    object Route : ScreenRoute()
}

@Composable
fun RememberScreen(
    viewModel: RememberViewModel = koinViewModel(),
) {
    RememberScreen(
       onNavigationButtonClick = viewModel::onNavigationButtonClick
    )
}

@Composable
private fun RememberScreen(
    onNavigationButtonClick: () -> Unit,
) {
    val contentVerticalAlignmentBias = -0.5f

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.menu_item_remember),
                icon = painterResource(R.drawable.icon_light),
                onGoBackClick = onNavigationButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Text(
                text = stringResource(R.string.remember_text),
                textAlign = TextAlign.Center,
                color = AppTheme.colors.primary,
                modifier = Modifier
                    .align(BiasAlignment(horizontalBias = 0f, verticalBias = contentVerticalAlignmentBias))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(Insets.Default)
            )
        }
    }
}

@Composable
@Preview
private fun PagerScreenPreview() {
    AppTheme {
        RememberScreen(
            onNavigationButtonClick = {},
        )
    }
}