package ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

abstract class ComposeRoute(
    val namedNavArguments: List<NamedNavArgument> = emptyList(),
) {
    /*
    Composable content should be passed throw constructor of this class ideally,
    but there is bug in some of Kotlin features.
    See https://issuetracker.google.com/issues/243119647#comment15
     */
    @Composable
    abstract fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry)

    private val params = namedNavArguments.map(NamedNavArgument::name)

    private val definitionBase = this::class.java.name
    val definition = "$definitionBase?${params.toQueryParams()}"

    fun withArgs(args: Map<String, String> = emptyMap()) =
        buildString {
            append(definitionBase)

            if (args.isNotEmpty()) {
                append('?')
                append(args.toQueryArgs())
            }
        }

    private fun Map<String, String>.toQueryArgs() =
        entries.joinToString(separator = "&") { "${it.key}=${it.value}" }

    private fun List<String>.toQueryParams() =
        joinToString(separator = "&") { "${it}={${it}}" }
}

fun NavGraphBuilder.composable(
    route: ComposeRoute
) {
    composable(
        route = route.definition,
        arguments = route.namedNavArguments,
        content = { navBackStackEntry ->
            with(route) {
                Content(navBackStackEntry)
            }
        },
    )
}

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: ComposeRoute,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.definition,
        modifier = modifier,
        builder = builder,
    )
}