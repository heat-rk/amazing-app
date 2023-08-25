package ru.heatalways.amazingasfuckapplication.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsViewModel

val viewModelsModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::FactsViewModel)
    viewModelOf(::InsultsViewModel)
    viewModelOf(::CatsViewModel)
}