package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.navigation.NavGraphBuilder
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.composable
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditScreen

fun NavGraphBuilder.buildGraph() {
    composable(MenuScreenRouteDefinition) { MenuScreen() }
    composable(FactsScreenRouteDefinition) { FactsScreen() }
    composable(InsultsScreenRouteDefinition) { InsultsScreen() }
    composable(CatsScreenRouteDefinition) { CatsScreen() }
    composable(MirrorScreenRouteDefinition) { MirrorScreen() }
    composable(PidorsScreenRouteDefinition) { PidorsScreen() }

    composable(PidorEditScreen.RouteDefinition) {
        PidorEditScreen(
            name = it.arguments?.getString(PidorEditScreen.NAME_PARAM) ?: "",
            photoPath = it.arguments?.getString(PidorEditScreen.PHOTO_PATH) ?: "",
        )
    }
}