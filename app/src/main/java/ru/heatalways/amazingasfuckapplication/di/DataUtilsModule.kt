package ru.heatalways.amazingasfuckapplication.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.common.utils.UriToFileSaver

val dataUtilsModule = module {
    single {
        UriToFileSaver(
            applicationContext = androidApplication()
        )
    }
}