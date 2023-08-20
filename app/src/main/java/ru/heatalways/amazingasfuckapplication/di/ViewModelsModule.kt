package ru.heatalways.amazingasfuckapplication.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuViewModel

val viewModelsModule = module {
    viewModel {
        MenuViewModel()
    }
}