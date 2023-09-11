package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

abstract class ScreenRoute(
    val params: List<NamedNavArgument> = emptyList(),
) {
    private val definitionBase = this::class.java.simpleName
    val definition = "$definitionBase?${params.toQueryParams()}"

    fun withArgs(args: Map<String, String> = emptyMap()) =
        "${definitionBase}?${args.toQueryArgs()}"

    private fun Map<String, String>.toQueryArgs() =
        entries.joinToString(separator = "&") { "${it.key}=${it.value}" }

    private fun List<NamedNavArgument>.toQueryParams() =
        joinToString(separator = "&") { "${it.name}={${it.name}}" }
}

fun NavGraphBuilder.composable(
    route: ScreenRoute,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route = route.definition,
        arguments = route.params,
        content = content,
    )
}