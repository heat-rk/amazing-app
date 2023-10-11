package ru.heatalways.amazingapplication.feature.insults.compose_impl.ui

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingapplication.common.utils.StringResource
import ru.heatalways.amazingapplication.common.utils.extract
import ru.heatalways.amazingapplication.core.composables.pager.PagerScreen
import ru.heatalways.amazingapplication.core.design.composables.shimmerEffect
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets
import ru.heatalways.amazingapplication.core.design.R as DesignR

@Composable
fun InsultsScreen(
    viewModel: InsultsViewModel = koinViewModel(),
    title: String,
) {
    PagerScreen(
        viewModel = viewModel,
        title = title,
        icon = painterResource(DesignR.drawable.icon_insult),
        content = {
            InsultsScreenContent(insult = it)
        },
        contentShimmer = {
            InsultsScreenContentShimmer()
        }
    )
}

@Composable
private fun InsultsScreenContent(insult: StringResource) {
    Text(
        text = insult.extract() ?: "-",
        textAlign = TextAlign.Center,
        color = AppTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(Insets.Default)
    )
}

@Composable
private fun InsultsScreenContentShimmer() {
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