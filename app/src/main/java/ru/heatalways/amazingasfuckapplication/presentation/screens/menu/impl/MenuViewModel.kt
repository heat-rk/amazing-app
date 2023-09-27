package ru.heatalways.amazingasfuckapplication.presentation.screens.menu.impl

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.feature.cats.api.navigation.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.api.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.api.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.impl.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.api.MirrorScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api.PidorsListScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.api.RememberScreenRoute

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