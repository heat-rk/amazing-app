package ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.ComposeRouter

val composeNavigationModule = module {
    singleOf(::ComposeRouter) { bind<Router>() }
}