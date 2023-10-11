package ru.heatalways.amazingasfuckapplication.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.common.utils.requireLongArg
import ru.heatalways.amazingasfuckapplication.common.utils.requireStringArg
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.ComposeRoute
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.NavHost
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.composable
import ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.ui.CatsScreen
import ru.heatalways.amazingasfuckapplication.feature.facts.compose_impl.ui.FactsScreen
import ru.heatalways.amazingasfuckapplication.feature.insults.compose_impl.ui.InsultsScreen
import ru.heatalways.amazingasfuckapplication.feature.menu.impl.ui.MenuScreen
import ru.heatalways.amazingasfuckapplication.feature.mirror.compose_impl.ui.MirrorScreen
import ru.heatalways.amazingasfuckapplication.feature.pidors.compose_impl.ui.PidorsScreen
import ru.heatalways.amazingasfuckapplication.feature.pidors.compose_impl.ui.edit.PidorEditScreen
import ru.heatalways.amazingasfuckapplication.feature.remember.compose_impl.ui.RememberScreen

object ComposeMenuScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            MenuScreen()
        }
    }
}

object ComposeCatsScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            CatsScreen(title = stringResource(id = R.string.menu_item_cats))
        }
    }
}

object ComposeFactsScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            FactsScreen(title = stringResource(id = R.string.menu_item_facts))
        }
    }
}

object ComposeInsultsScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            InsultsScreen(title = stringResource(id = R.string.menu_item_insults))
        }
    }
}

object ComposeMirrorScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            MirrorScreen(title = stringResource(id = R.string.menu_item_mirror))
        }
    }
}

object ComposePidorsScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            PidorsScreen(title = stringResource(id = R.string.menu_item_pidors_list))
        }
    }
}

object ComposePidorEditScreen {
    const val ID_PARAM = "id"
    const val NAME_PARAM = "name"
    const val PHOTO_PATH = "photo_path"

    object Route : ComposeRoute(
        namedNavArguments = listOf(
            navArgument(ID_PARAM) {
                type = NavType.LongType
            },

            navArgument(NAME_PARAM) {
                type = NavType.StringType
            },

            navArgument(PHOTO_PATH) {
                type = NavType.StringType
            }
        )
    ) {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            PidorEditScreen(
                id = navBackStackEntry.requireLongArg(ID_PARAM),
                name = navBackStackEntry.requireStringArg(NAME_PARAM),
                photoPath = navBackStackEntry.requireStringArg(PHOTO_PATH),
            )
        }

    }
}

object ComposeRememberScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            RememberScreen(title = stringResource(id = R.string.menu_item_remember))
        }
    }
}

private fun NavGraphBuilder.buildGraph() {
    composable(ComposeMenuScreen.Route)
    composable(ComposeCatsScreen.Route)
    composable(ComposeFactsScreen.Route)
    composable(ComposeInsultsScreen.Route)
    composable(ComposeMirrorScreen.Route)
    composable(ComposePidorsScreen.Route)
    composable(ComposePidorEditScreen.Route)
    composable(ComposeRememberScreen.Route)
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ComposeMenuScreen.Route,
        builder = NavGraphBuilder::buildGraph,
        modifier = modifier,
    )
}