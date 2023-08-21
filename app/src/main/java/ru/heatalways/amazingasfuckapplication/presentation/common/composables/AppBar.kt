@file:OptIn(ExperimentalMaterial3Api::class)

package ru.heatalways.amazingasfuckapplication.presentation.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.presentation.styles.Sizes

@Composable
fun AppBar(
    title: String,
    icon: Painter,
    onGoBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentSize()
            ) {
                if (onGoBackClick != null) {
                    Spacer(modifier = Modifier.width(Insets.Small))
                }

                Text(
                    text = title,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.width(Insets.Small))

                Image(
                    painter = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.primary),
                    modifier = Modifier.width(Sizes.AppBarIcon)
                )
            }
        },
        navigationIcon = {
             if (onGoBackClick != null) {
                 IconButton(
                     onClick = { onGoBackClick?.invoke() }
                 ) {
                     Image(
                         painter = painterResource(R.drawable.icon_arrow_left),
                         contentDescription = stringResource(R.string.go_back_icon_content_description),
                         colorFilter = ColorFilter.tint(AppTheme.colors.primary),
                         modifier = Modifier
                             .width(Sizes.AppBarIcon)
                     )
                 }
             }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.background,
            navigationIconContentColor = AppTheme.colors.primary,
            titleContentColor = AppTheme.colors.primary,
            actionIconContentColor = AppTheme.colors.primary,
        ),
        modifier = modifier
    )
}

@Composable
@Preview
fun AppBarPreview() {
    AppTheme {
        AppBar(
            title = "Какой-то экран",
            icon = painterResource(R.drawable.icon_cat),
            onGoBackClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}