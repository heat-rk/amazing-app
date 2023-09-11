package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api

sealed interface RoutingAction {
    data class NavigateTo(
        val route: ScreenRoute,
        val args: Map<String, String> = emptyMap()
    ) : RoutingAction

    object NavigateBack : RoutingAction
}