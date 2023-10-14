package ru.heatalways.amazingapplication.feature.cats.compose_impl.di

import androidx.lifecycle.ViewModelProvider
import scout.Component

internal object CatsComponent : Component(catsScope) {
    val catsViewModelFactory: ViewModelProvider.Factory
        get() = get<CatsViewModelFactory>().instance
}