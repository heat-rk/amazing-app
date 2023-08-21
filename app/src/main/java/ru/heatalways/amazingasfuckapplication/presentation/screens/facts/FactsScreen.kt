package ru.heatalways.amazingasfuckapplication.presentation.screens.facts

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerScreen

object FactsScreenRoute : ScreenRoute()
@Composable
fun FactsScreen(viewModel: FactsViewModel = koinViewModel()) {
    PagerScreen(
        viewModel = viewModel,
        title = stringResource(R.string.menu_item_facts),
        icon = painterResource(R.drawable.icon_boobs),
        content = {

        },
        contentShimmer = {

        }
    )
}