package ru.heatalways.amazingasfuckapplication.core.navigation.api

import kotlinx.coroutines.flow.Flow

interface Router {
    val actions: Flow<RoutingAction>

    suspend fun navigate(
        route: ScreenRoute,
        args: Map<String, String> = emptyMap()
    )

    suspend fun navigateBack()
}