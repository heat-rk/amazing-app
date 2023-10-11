package ru.heatalways.amazingasfuckapplication.feature.remember.compose_impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.feature.remember.impl.ui.RememberViewModel

private val internalRememberModule = module {
    viewModelOf(::RememberViewModel)
}

val rememberModule = module {
    includes(internalRememberModule)
}