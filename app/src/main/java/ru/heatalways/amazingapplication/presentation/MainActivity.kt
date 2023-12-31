package ru.heatalways.amazingapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.navigation.api.Router
import ru.heatalways.amazingapplication.core.navigation.api.RoutingAction
import ru.heatalways.amazingapplication.di.AppComponent
import ru.heatalways.amazingapplication.presentation.navigation.AppNavHost
import ru.heatalways.amazingapplication.presentation.navigation.composeRoute

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

                AppNavHost(
                    navController = navController,
                    modifier = Modifier.background(backgroundColor),
                )
            }
        }
    }

    @Composable
    private fun LaunchedRouterEffect(
        navController: NavController,
        router: Router = AppComponent.router,
    ) {
        LaunchedEffect(navController, router) {
            router.actions
                .onEach { action ->
                    when (action) {
                        RoutingAction.NavigateBack -> {
                            navController.popBackStack()
                        }
                        is RoutingAction.NavigateTo -> {
                            navController.navigate(composeRoute(action.route))
                        }
                    }
                }
                .launchIn(this)
        }
    }
}