package ru.heatalways.amazingapplication.presentation.navigation

import ru.heatalways.amazingapplication.core.navigation.api.Route
import ru.heatalways.amazingapplication.feature.cats.api.navigation.CatsScreenRoute
import ru.heatalways.amazingapplication.feature.facts.api.navigation.FactsScreenRoute
import ru.heatalways.amazingapplication.feature.insults.api.navigation.InsultsScreenRoute
import ru.heatalways.amazingapplication.feature.mirror.api.navigation.MirrorScreenRoute
import ru.heatalways.amazingapplication.feature.hate_top.api.navigation.HateTopUnitEditScreenRoute
import ru.heatalways.amazingapplication.feature.hate_top.api.navigation.HateTopListScreenRoute

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
    HateTopListScreenRoute -> {
        ComposeHateTopScreen.Route.withArgs()
    }
    is HateTopUnitEditScreenRoute -> {
        ComposeHateTopUnitEditScreen.Route.withArgs(
            args = mapOf(
                ComposeHateTopUnitEditScreen.ID_PARAM to route.id.toString(),
                ComposeHateTopUnitEditScreen.NAME_PARAM to route.name,
                ComposeHateTopUnitEditScreen.PHOTO_PATH to route.photoPath,
            )
        )
    }
    else -> {
        throw IllegalArgumentException("Unknown $route route. Check compose route handler and navigation graph!")
    }
}