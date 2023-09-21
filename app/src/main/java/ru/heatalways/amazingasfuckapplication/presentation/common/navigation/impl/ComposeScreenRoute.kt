package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute

abstract class ComposeScreenRoute(
    params: List<NamedNavArgument> = emptyList(),
    val content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) : ScreenRoute(params)

fun NavGraphBuilder.composable(
    route: ComposeScreenRoute
) {
    composable(
        route = route.definition,
        arguments = route.params,
        content = route.content,
    )
}