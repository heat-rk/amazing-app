package ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.heatalways.amazingasfuckapplication.core.navigation.api.ScreenRoute

abstract class ComposeScreenRoute(
    val namedNavArguments: List<NamedNavArgument> = emptyList(),
) : ScreenRoute(namedNavArguments.map { it.name }) {
    /*
    Composable content should be passed throw constructor of this class ideally,
    but there is bug in some of Kotlin features.
    See https://issuetracker.google.com/issues/243119647#comment15
     */
    @Composable
    abstract fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry)
}

fun NavGraphBuilder.composable(
    route: ComposeScreenRoute
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