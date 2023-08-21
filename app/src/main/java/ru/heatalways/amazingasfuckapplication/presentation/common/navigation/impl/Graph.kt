package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreenRoute

fun NavGraphBuilder.buildGraph() {
    composable(MenuScreenRoute.route) { MenuScreen() }
    composable(FactsScreenRoute.route) { FactsScreen() }
}