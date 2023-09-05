package ru.heatalways.amazingasfuckapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl.ComposeRouter
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl.buildGraph
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()
                val backgroundColor = AppTheme.colors.background

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = backgroundColor
                    )

                    systemUiController.setNavigationBarColor(
                        color = backgroundColor
                    )
                }

                val router = (koinInject<Router>() as? ComposeRouter)

                if (router != null) {
                    LaunchedEffect(Unit) {
                        router.routing
                            .onEach { event -> event(navController) }
                            .launchIn(this)
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = MenuScreenRouteDefinition.route,
                    modifier = Modifier.background(backgroundColor),
                    builder = NavGraphBuilder::buildGraph
                )
            }
        }
    }
}