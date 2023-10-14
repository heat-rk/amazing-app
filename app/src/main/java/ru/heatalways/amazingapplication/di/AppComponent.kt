package ru.heatalways.amazingapplication.di

import ru.heatalways.amazingapplication.core.navigation.api.Router
import scout.Component

object AppComponent : Component(appScope) {
    val router: Router get() = get()
}