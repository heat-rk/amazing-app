package ru.heatalways.amazingasfuckapplication.presentation.common.sharing.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.common.sharing.api.Sharing
import ru.heatalways.amazingasfuckapplication.presentation.common.sharing.impl.AndroidSharing

val sharingModule = module {
    factory<Sharing> {
        AndroidSharing(
            context = androidApplication()
        )
    }
}