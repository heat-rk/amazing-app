package ru.heatalways.amazingasfuckapplication.presentation.screens.facts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.shimmerEffect
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerScreen
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.extract

object FactsScreenRoute : ScreenRoute()

@Composable
fun FactsScreen(viewModel: FactsViewModel = koinViewModel()) {
    PagerScreen(
        viewModel = viewModel,
        title = stringResource(R.string.menu_item_facts),
        icon = painterResource(R.drawable.icon_boobs),
        content = {
            FactsScreenContent(fact = it)
        },
        contentShimmer = {
            FactsScreenContentShimmer()
        }
    )
}

@Composable
private fun FactsScreenContent(fact: StringResource) {
    Text(
        text = fact.extract() ?: "-",
        textAlign = TextAlign.Center,
        color = AppTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(Insets.Default)
    )
}

@Composable
private fun FactsScreenContentShimmer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val textLineHeight: Dp
        val lineSpacing: Dp

        with(LocalDensity.current) {
            textLineHeight = LocalTextStyle.current.fontSize.toDp()
            lineSpacing = LocalTextStyle.current.lineHeight.toDp() - textLineHeight
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(textLineHeight)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(lineSpacing))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(textLineHeight)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(lineSpacing))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(textLineHeight)
                .clip(AppTheme.shapes.medium)
                .shimmerEffect()
        )
    }
}