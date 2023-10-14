package ru.heatalways.amazingapplication.feature.insults.compose_impl.di

import androidx.lifecycle.ViewModelProvider
import scout.Component

internal object InsultsComponent : Component(insultsScope) {
    val insultsViewModelFactory: ViewModelProvider.Factory
        get() = get<InsultsViewModelFactory>().instance
}