package ru.heatalways.amazingasfuckapplication.feature.sharing.android_impl.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.feature.sharing.android_impl.AndroidSharing
import ru.heatalways.amazingasfuckapplication.feature.sharing.api.Sharing

val androidSharingModule = module {
    factory<Sharing> {
        AndroidSharing(
            context = androidApplication()
        )
    }
}