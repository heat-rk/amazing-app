@file:OptIn(ExperimentalMaterial3Api::class)

package ru.heatalways.amazingapplication.core.design.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.heatalways.amazingapplication.common.utils.PainterResource
import ru.heatalways.amazingapplication.common.utils.StringResource
import ru.heatalways.amazingapplication.common.utils.extract
import ru.heatalways.amazingapplication.common.utils.painterRes
import ru.heatalways.amazingapplication.common.utils.strRes
import ru.heatalways.amazingapplication.core.design.R
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets
import ru.heatalways.amazingapplication.core.design.styles.Sizes

@Composable
fun AppBar(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    actions: List<AppBarActionItem> = emptyList(),
    containerColor: Color = AppTheme.colors.background,
    onGoBackClick: (() -> Unit)? = null,
) {
    val color = AppTheme.colors.primary

    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
            ) {
                if (onGoBackClick != null) {
                    Spacer(modifier = Modifier.width(Insets.Small))
                }

                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.titleMedium,
                    modifier = Modifier
                        .rectangularBackgroundLighting(color)
                )

                Spacer(modifier = Modifier.requiredWidth(Insets.Small))

                Image(
                    painter = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .requiredWidth(Sizes.AppBarIcon)
                        .radialBackgroundLighting(color)
                )
            }
        },
        navigationIcon = {
             if (onGoBackClick != null) {
                 IconButton(
                     onClick = { onGoBackClick.invoke() },
                     modifier = Modifier
                         .radialBackgroundLighting(color)
                 ) {
                     Image(
                         painter = painterResource(R.drawable.icon_arrow_left),
                         contentDescription = stringResource(R.string.go_back_icon_content_description),
                         colorFilter = ColorFilter.tint(color),
                         modifier = Modifier
                             .width(Sizes.AppBarIcon)
                     )
                 }
             }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            navigationIconContentColor = AppTheme.colors.primary,
            titleContentColor = AppTheme.colors.primary,
            actionIconContentColor = AppTheme.colors.primary,
        ),
        actions = {
            actions.forEach { action ->
                IconButton(
                    onClick = action.onClick
                ) {
                    Icon(
                        painter = action.icon.extract(),
                        contentDescription = action.contentDescription.extract(),
                        modifier = Modifier
                            .width(Sizes.AppBarIcon)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview
private fun AppBarPreview() {
    AppTheme {
        AppBar(
            title = "Какой-то экран",
            icon = painterResource(R.drawable.icon_cat),
            actions = listOf(
                AppBarActionItem(
                    icon = painterRes(R.drawable.icon_leaderboard),
                    contentDescription = strRes(""),
                    onClick = {},
                )
            ),
            onGoBackClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

data class AppBarActionItem(
    val icon: PainterResource,
    val contentDescription: StringResource,
    val onClick: () -> Unit,
)