package ru.heatalways.amazingapplication.core.navigation.compose_impl.di

import ru.heatalways.amazingapplication.core.navigation.api.Router
import ru.heatalways.amazingapplication.core.navigation.compose_impl.ComposeRouter
import scout.definition.Registry

fun Registry.useComposeNavigationBeans() {
    singleton<Router> { ComposeRouter() }
}