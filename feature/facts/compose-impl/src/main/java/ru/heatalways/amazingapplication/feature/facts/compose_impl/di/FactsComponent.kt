package ru.heatalways.amazingapplication.feature.facts.compose_impl.di

import androidx.lifecycle.ViewModelProvider
import scout.Component

internal object FactsComponent : Component(factsScope) {
    val factsViewModelFactory: ViewModelProvider.Factory
        get() = get<FactsViewModelFactory>().instance
}