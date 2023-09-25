package ru.heatalways.amazingasfuckapplication.di

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.common.utils.requireLongArg
import ru.heatalways.amazingasfuckapplication.common.utils.requireStringArg
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.ComposeScreenRoute
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.composable
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.api.CatsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.impl.CatsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.api.FactsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.impl.FactsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.api.InsultsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.impl.InsultsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.api.MirrorScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.impl.MirrorScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api.PidorsScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.impl.PidorsScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.impl.edit.PidorEditScreen
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.api.RememberScreenNavigator
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.impl.RememberScreen

object MenuScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            MenuScreen()
        }
    }
}

private object CatsScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            CatsScreen()
        }
    }
}

private object FactsScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            FactsScreen()
        }
    }
}

private object InsultsScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            InsultsScreen()
        }
    }
}

private object MirrorScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            MirrorScreen()
        }
    }
}

private object PidorsScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            PidorsScreen()
        }
    }
}

private object PidorEditScreen {
    const val ID_PARAM = "id"
    const val NAME_PARAM = "name"
    const val PHOTO_PATH = "photo_path"

    object Route : ComposeScreenRoute(
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

private object RememberScreen {
    object Route : ComposeScreenRoute() {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            RememberScreen()
        }
    }
}

val navigationGraphModule = module {
    factory<NavGraphBuilder.() -> Unit> {
        {
            composable(MenuScreen.Route)
            composable(CatsScreen.Route)
            composable(FactsScreen.Route)
            composable(InsultsScreen.Route)
            composable(MirrorScreen.Route)
            composable(PidorsScreen.Route)
            composable(PidorEditScreen.Route)
            composable(RememberScreen.Route)
        }
    }

    factory<CatsScreenNavigator> {
        object : CatsScreenNavigator {
            override suspend fun navigate() {
                get<Router>().navigate(CatsScreen.Route)
            }
        }
    }

    factory<FactsScreenNavigator> {
        object : FactsScreenNavigator {
            override suspend fun navigate() {
                get<Router>().navigate(FactsScreen.Route)
            }
        }
    }

    factory<InsultsScreenNavigator> {
        object : InsultsScreenNavigator {
            override suspend fun navigate() {
                get<Router>().navigate(InsultsScreen.Route)
            }
        }
    }

    factory<MirrorScreenNavigator> {
        object : MirrorScreenNavigator {
            override suspend fun navigate() {
                get<Router>().navigate(MirrorScreen.Route)
            }
        }
    }

    factory<PidorsScreenNavigator> {
        object : PidorsScreenNavigator {
            override suspend fun navigateToList() {
                get<Router>().navigate(PidorsScreen.Route)
            }

            override suspend fun navigateToEdition(id: Long, name: String, photoPath: String) {
                get<Router>().navigate(
                    route = PidorEditScreen.Route,
                    args = mapOf(
                        PidorEditScreen.ID_PARAM to id.toString(),
                        PidorEditScreen.NAME_PARAM to name,
                        PidorEditScreen.PHOTO_PATH to photoPath,
                    )
                )
            }
        }
    }

    factory<RememberScreenNavigator> {
        object : RememberScreenNavigator {
            override suspend fun navigate() {
                get<Router>().navigate(RememberScreen.Route)
            }
        }
    }
}