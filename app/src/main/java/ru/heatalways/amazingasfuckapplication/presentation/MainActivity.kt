package ru.heatalways.amazingasfuckapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import ru.heatalways.amazingasfuckapplication.core.design.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.core.navigation.api.RoutingAction
import ru.heatalways.amazingasfuckapplication.core.navigation.api.routeWithArgs
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.NavHost
import ru.heatalways.amazingasfuckapplication.di.MenuScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()
                val backgroundColor = AppTheme.colors.background

                LaunchedEffect(systemUiController, backgroundColor) {
                    systemUiController.setStatusBarColor(
                        color = backgroundColor
                    )

                    systemUiController.setNavigationBarColor(
                        color = backgroundColor
                    )
                }

                LaunchedRouterEffect(navController = navController)

                NavHost(
                    navController = navController,
                    startDestination = MenuScreen.Route,
                    modifier = Modifier.background(backgroundColor),
                    builder = koinInject()
                )
            }
        }
    }

    @Composable
    private fun LaunchedRouterEffect(
        navController: NavController,
        router: Router = koinInject(),
    ) {
        LaunchedEffect(navController, router) {
            router.actions
                .onEach { action ->
                    when (action) {
                        RoutingAction.NavigateBack -> {
                            navController.popBackStack()
                        }
                        is RoutingAction.NavigateTo -> {
                            navController.navigate(action.routeWithArgs())
                        }
                    }
                }
                .launchIn(this)
        }
    }
}