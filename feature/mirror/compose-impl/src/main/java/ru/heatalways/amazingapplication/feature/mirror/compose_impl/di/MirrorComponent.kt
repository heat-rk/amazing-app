package ru.heatalways.amazingapplication.feature.mirror.compose_impl.di

import androidx.lifecycle.ViewModelProvider
import scout.Component

internal object MirrorComponent : Component(mirrorScope) {
    val mirrorViewModelFactory: ViewModelProvider.Factory
        get() = get<MirrorViewModelFactory>().instance
}