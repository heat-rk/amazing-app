package ru.heatalways.amazingapplication.feature.menu.impl.di

import androidx.lifecycle.ViewModelProvider
import scout.Component

object MenuComponent : Component(menuScope) {
    val menuViewModelFactory: ViewModelProvider.Factory
        get() = get<MenuViewModelFactory>().instance
}