package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.navigation.NavController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRouteDefinition

class ComposeRouter : Router {
    private val _routing = Channel<NavController.() -> Unit>(Channel.BUFFERED)
    val routing = _routing.receiveAsFlow()

    override suspend fun navigate(route: ScreenRoute) {
        _routing.send { navigate(route.route) }
    }

    override suspend fun navigateBack() {
        _routing.send { popBackStack() }
    }
}