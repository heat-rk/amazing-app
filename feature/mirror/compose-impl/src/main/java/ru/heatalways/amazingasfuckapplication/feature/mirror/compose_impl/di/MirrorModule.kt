package ru.heatalways.amazingasfuckapplication.feature.mirror.compose_impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.feature.mirror.compose_impl.ui.MirrorViewModel

private val internalMirrorModule = module {
    viewModelOf(::MirrorViewModel)
}

val mirrorModule = module {
    includes(internalMirrorModule)
}