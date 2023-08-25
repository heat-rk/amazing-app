package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreenRoute

fun NavGraphBuilder.buildGraph() {
    composable(MenuScreenRoute.route) { MenuScreen() }
    composable(FactsScreenRoute.route) { FactsScreen() }
    composable(InsultsScreenRoute.route) { InsultsScreen() }
    composable(CatsScreenRoute.route) { CatsScreen() }
}