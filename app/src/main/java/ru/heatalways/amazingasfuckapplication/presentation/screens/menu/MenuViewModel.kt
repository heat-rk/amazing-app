package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.api.CatsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.api.FactsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.api.InsultsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.api.MirrorScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api.PidorsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.api.RememberScreenNavigator

class MenuViewModel(
    private val mirrorScreenNavigator: MirrorScreenNavigator,
    private val catsScreenNavigator: CatsScreenNavigator,
    private val factsScreenNavigator: FactsScreenNavigator,
    private val insultsScreenNavigator: InsultsScreenNavigator,
    private val pidorsScreenNavigator: PidorsScreenNavigator,
    private val rememberScreenNavigator: RememberScreenNavigator,
) : ViewModel(), ContainerHost<ViewState, Unit> {

    override val container = container<ViewState, Unit>(
        initialState = ViewState(
            items = MenuItem.values().toList().toImmutableList()
        )
    )

    fun onMenuItemClick(item: MenuItem) = intent {
        when (item) {
            MenuItem.MIRROR -> mirrorScreenNavigator.navigate()
            MenuItem.CATS -> catsScreenNavigator.navigate()
            MenuItem.FACTS -> factsScreenNavigator.navigate()
            MenuItem.INSULTS -> insultsScreenNavigator.navigate()
            MenuItem.PIDORS_LIST -> pidorsScreenNavigator.navigateToList()
            MenuItem.REMEMBER -> rememberScreenNavigator.navigate()
        }
    }
}