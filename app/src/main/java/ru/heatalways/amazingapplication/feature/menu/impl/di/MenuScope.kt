package ru.heatalways.amazingapplication.feature.menu.impl.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.heatalways.amazingapplication.di.appScope
import ru.heatalways.amazingapplication.feature.menu.impl.ui.MenuViewModel
import scout.scope

val menuScope = scope("menu_scope") {
    dependsOn(appScope)

    singleton<MenuViewModelFactory> {
        MenuViewModelFactory(
            viewModelFactory {
                initializer {
                    MenuViewModel(
                        router = get()
                    )
                }
            }
        )
    }
}