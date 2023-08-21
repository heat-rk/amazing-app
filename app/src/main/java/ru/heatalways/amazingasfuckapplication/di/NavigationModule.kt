package ru.heatalways.amazingasfuckapplication.di

import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.impl.ComposeRouter

val navigationModule = module {
    single<Router> { ComposeRouter() }
}