package ru.heatalways.amazingasfuckapplication.core.data.utils.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.data.utils.UriToFileSaver

val dataUtilsModule = module {
    single {
        UriToFileSaver(
            applicationContext = androidApplication()
        )
    }
}