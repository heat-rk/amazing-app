package ru.heatalways.amazingapplication.core.data.utils.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingapplication.core.data.utils.UriToFileSaver

val dataUtilsModule = module {
    single {
        UriToFileSaver(
            applicationContext = androidApplication()
        )
    }
}