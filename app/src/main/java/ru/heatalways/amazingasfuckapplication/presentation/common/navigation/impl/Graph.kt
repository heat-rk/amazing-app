package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.navigation.NavGraphBuilder
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.RememberScreen

fun NavGraphBuilder.buildGraph() {
    composable(MenuScreen.Route)
    composable(FactsScreen.Route)
    composable(InsultsScreen.Route)
    composable(CatsScreen.Route)
    composable(MirrorScreen.Route)
    composable(PidorsScreen.Route)
    composable(RememberScreen.Route)
    composable(PidorEditScreen.Route)
}