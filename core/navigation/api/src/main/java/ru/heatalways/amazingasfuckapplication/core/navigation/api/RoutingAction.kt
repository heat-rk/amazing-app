package ru.heatalways.amazingasfuckapplication.core.navigation.api

sealed interface RoutingAction {
    data class NavigateTo(
        val route: ScreenRoute,
        val args: Map<String, String> = emptyMap()
    ) : RoutingAction

    object NavigateBack : RoutingAction
}

fun RoutingAction.NavigateTo.routeWithArgs() =
    route.withArgs(args)