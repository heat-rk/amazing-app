package ru.heatalways.amazingapplication.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.heatalways.amazingapplication.R
import ru.heatalways.amazingapplication.common.utils.requireLongArg
import ru.heatalways.amazingapplication.common.utils.requireStringArg
import ru.heatalways.amazingapplication.core.navigation.compose_impl.ComposeRoute
import ru.heatalways.amazingapplication.core.navigation.compose_impl.NavHost
import ru.heatalways.amazingapplication.core.navigation.compose_impl.composable
import ru.heatalways.amazingapplication.feature.cats.compose_impl.ui.CatsScreen
import ru.heatalways.amazingapplication.feature.facts.compose_impl.ui.FactsScreen
import ru.heatalways.amazingapplication.feature.insults.compose_impl.ui.InsultsScreen
import ru.heatalways.amazingapplication.feature.menu.impl.ui.MenuScreen
import ru.heatalways.amazingapplication.feature.mirror.compose_impl.ui.MirrorScreen
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopScreen
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditScreen

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

object ComposeHateTopScreen {
    object Route : ComposeRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            HateTopScreen(title = stringResource(id = R.string.menu_item_hate_top))
        }
    }
}

object ComposeHateTopUnitEditScreen {
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
            HateTopUnitEditScreen(
                id = navBackStackEntry.requireLongArg(ID_PARAM),
                name = navBackStackEntry.requireStringArg(NAME_PARAM),
                photoPath = navBackStackEntry.requireStringArg(PHOTO_PATH),
            )
        }

    }
}

private fun NavGraphBuilder.buildGraph() {
    composable(ComposeMenuScreen.Route)
    composable(ComposeCatsScreen.Route)
    composable(ComposeFactsScreen.Route)
    composable(ComposeInsultsScreen.Route)
    composable(ComposeMirrorScreen.Route)
    composable(ComposeHateTopScreen.Route)
    composable(ComposeHateTopUnitEditScreen.Route)
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