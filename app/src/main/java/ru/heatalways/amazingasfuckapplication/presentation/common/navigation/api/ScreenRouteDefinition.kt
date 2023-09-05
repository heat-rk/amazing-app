package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

abstract class ScreenRouteDefinition(
    val params: List<NamedNavArgument> = emptyList(),
) {
    val routeBase = this::class.java.simpleName
    val route = "$routeBase?${params.toQueryParams()}"

    private fun List<NamedNavArgument>.toQueryParams() =
        joinToString(separator = "&") { "${it.name}={${it.name}}" }
}

class ScreenRoute(
    definition: ScreenRouteDefinition,
    params: Map<String, String> = emptyMap(),
) {
    val route = "${definition.routeBase}?${params.toQueryParams()}"

    private fun Map<String, String>.toQueryParams() =
        entries.joinToString(separator = "&") { "${it.key}=${it.value}" }
}

fun NavGraphBuilder.composable(
    route: ScreenRouteDefinition,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route = route.route,
        arguments = route.params,
        content = content,
    )
}