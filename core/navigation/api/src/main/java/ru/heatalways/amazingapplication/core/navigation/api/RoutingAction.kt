package ru.heatalways.amazingapplication.core.navigation.api

sealed interface RoutingAction {
    data class NavigateTo(val route: Route) : RoutingAction

    object NavigateBack : RoutingAction
}