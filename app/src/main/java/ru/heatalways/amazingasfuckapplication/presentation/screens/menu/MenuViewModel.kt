package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.RememberScreen

class MenuViewModel(
    private val router: Router
) : ViewModel(), ContainerHost<ViewState, Unit> {

    override val container = container<ViewState, Unit>(
        initialState = ViewState(
            items = MenuItem.values().toList().toImmutableList()
        )
    )

    fun onMenuItemClick(item: MenuItem) = intent {
        when (item) {
            MenuItem.MIRROR -> router.navigate(MirrorScreenRoute)
            MenuItem.CATS -> router.navigate(CatsScreenRoute)
            MenuItem.FACTS -> router.navigate(FactsScreenRoute)
            MenuItem.INSULTS -> router.navigate(InsultsScreenRoute)
            MenuItem.PIDORS_LIST -> router.navigate(PidorsScreenRoute)
            MenuItem.REMEMBER -> router.navigate(RememberScreen.Route)
        }
    }
}