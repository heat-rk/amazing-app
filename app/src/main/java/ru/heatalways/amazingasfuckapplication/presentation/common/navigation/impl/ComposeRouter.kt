package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.RoutingAction
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute

class ComposeRouter : Router {
    private val _actions = Channel<RoutingAction>(Channel.BUFFERED)
    override val actions = _actions.receiveAsFlow()

    override suspend fun navigate(route: ScreenRoute, args: Map<String, String>) {
        action(RoutingAction.NavigateTo(route, args))
    }

    override suspend fun navigateBack() {
        action(RoutingAction.NavigateBack)
    }

    private suspend fun action(action: RoutingAction) {
        _actions.send(action)
    }
}