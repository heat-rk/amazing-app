package ru.heatalways.amazingasfuckapplication.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl.ComposeRouter

val navigationModule = module {
    singleOf(::ComposeRouter) { bind<Router>() }
}