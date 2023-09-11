package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.navigation.NavGraphBuilder
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.composable
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.RememberScreen

fun NavGraphBuilder.buildGraph() {
    composable(MenuScreenRoute) { MenuScreen() }
    composable(FactsScreenRoute) { FactsScreen() }
    composable(InsultsScreenRoute) { InsultsScreen() }
    composable(CatsScreenRoute) { CatsScreen() }
    composable(MirrorScreenRoute) { MirrorScreen() }
    composable(PidorsScreenRoute) { PidorsScreen() }
    composable(RememberScreen.Route) { RememberScreen() }

    composable(PidorEditScreen.Route) {
        PidorEditScreen(
            name = it.arguments?.getString(PidorEditScreen.NAME_PARAM) ?: "",
            photoPath = it.arguments?.getString(PidorEditScreen.PHOTO_PATH) ?: "",
        )
    }
}