package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreenRoute

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
            MenuItem.MIRROR -> router.navigate(MirrorScreenRoute)
            MenuItem.CATS -> router.navigate(CatsScreenRoute)
            MenuItem.FACTS -> router.navigate(FactsScreenRoute)
            MenuItem.INSULTS -> router.navigate(InsultsScreenRoute)
            MenuItem.PIDORS_LIST -> Unit
        }
    }
}