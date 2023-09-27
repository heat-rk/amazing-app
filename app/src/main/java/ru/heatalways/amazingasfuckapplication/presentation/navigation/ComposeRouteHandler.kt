package ru.heatalways.amazingasfuckapplication.presentation.navigation

import ru.heatalways.amazingasfuckapplication.core.navigation.api.Route
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.api.CatsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.api.FactsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.api.InsultsScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.api.MirrorScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api.PidorEditScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api.PidorsListScreenRoute

fun composeRoute(route: Route) = when (route) {
    CatsScreenRoute -> {
        ComposeCatsScreen.Route.withArgs()
    }
    FactsScreenRoute -> {
        ComposeFactsScreen.Route.withArgs()
    }
    InsultsScreenRoute -> {
        ComposeInsultsScreen.Route.withArgs()
    }
    MirrorScreenRoute -> {
        ComposeMirrorScreen.Route.withArgs()
    }
    PidorsListScreenRoute -> {
        ComposePidorsScreen.Route.withArgs()
    }
    is PidorEditScreenRoute -> {
        ComposePidorEditScreen.Route.withArgs(
            args = mapOf(
                ComposePidorEditScreen.ID_PARAM to route.id.toString(),
                ComposePidorEditScreen.NAME_PARAM to route.name,
                ComposePidorEditScreen.PHOTO_PATH to route.photoPath,
            )
        )
    }
    else -> {
        throw IllegalArgumentException("Unknown $route route. Check compose route handler and navigation graph!")
    }
}