package ru.heatalways.amazingasfuckapplication.feature.menu.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.feature.menu.impl.ui.MenuViewModel

private val internalMenuModule = module {
    viewModelOf(::MenuViewModel)
}

val menuModule = module {
    includes(internalMenuModule)
}