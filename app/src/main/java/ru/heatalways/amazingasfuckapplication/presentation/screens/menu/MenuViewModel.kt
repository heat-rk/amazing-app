package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreenRouteDefinition

class MenuViewModel(
    private val router: Router
) : MviViewModel<ViewState, Intent>(
    initialState = ViewState(
        items = MenuItem.values().toList().toImmutableList()
    )
) {
    override fun onNewIntent(intent: Intent) {
        when (intent) {
            is Intent.OnMenuItemClick -> onMenuItemClick(intent.item)
        }
    }

    private fun onMenuItemClick(item: MenuItem) = viewModelScope.launch {
        when (item) {
            MenuItem.MIRROR -> router.navigate(ScreenRoute(MirrorScreenRouteDefinition))
            MenuItem.CATS -> router.navigate(ScreenRoute(CatsScreenRouteDefinition))
            MenuItem.FACTS -> router.navigate(ScreenRoute(FactsScreenRouteDefinition))
            MenuItem.INSULTS -> router.navigate(ScreenRoute(InsultsScreenRouteDefinition))
            MenuItem.PIDORS_LIST -> router.navigate(ScreenRoute(PidorsScreenRouteDefinition))
        }
    }
}