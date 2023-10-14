package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di

import androidx.lifecycle.ViewModelProvider
import scout.Component

internal object HateTopComponent : Component(hateTopScope) {
    val hateTopViewModelFactory: ViewModelProvider.Factory
        get() = get<HateTopViewModelFactory>().instance

    val hateTopUnitEditViewModelFactory: ViewModelProvider.Factory
        get() = get<HateTopUnitEditViewModelFactory>().instance
}