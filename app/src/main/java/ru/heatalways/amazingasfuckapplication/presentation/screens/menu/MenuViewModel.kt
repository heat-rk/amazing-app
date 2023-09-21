package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreen
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
            MenuItem.MIRROR -> router.navigate(MirrorScreen.Route)
            MenuItem.CATS -> router.navigate(CatsScreen.Route)
            MenuItem.FACTS -> router.navigate(FactsScreen.Route)
            MenuItem.INSULTS -> router.navigate(InsultsScreen.Route)
            MenuItem.PIDORS_LIST -> router.navigate(PidorsScreen.Route)
            MenuItem.REMEMBER -> router.navigate(RememberScreen.Route)
        }
    }
}