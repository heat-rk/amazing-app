package ru.heatalways.amazingasfuckapplication.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuViewModel

val viewModelsModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::FactsViewModel)
}