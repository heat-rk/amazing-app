package ru.heatalways.amazingasfuckapplication.feature.menu.impl.ui

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.feature.cats.api.navigation.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.feature.facts.api.navigation.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.feature.insults.api.navigation.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.feature.menu.impl.ui.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.feature.mirror.api.navigation.MirrorScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api.PidorsListScreenRoute
import ru.heatalways.amazingasfuckapplication.feature.remember.api.navigation.RememberScreenRoute

class MenuViewModel(
    private val router: Router,
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
            MenuItem.PIDORS_LIST -> router.navigate(PidorsListScreenRoute)
            MenuItem.REMEMBER -> router.navigate(RememberScreenRoute)
        }
    }
}