package ru.heatalways.amazingasfuckapplication.core.data.temp_files.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingasfuckapplication.core.data.temp_files.TempFilesStorage

val tempFilesStorageModule = module {
    factory {
        TempFilesStorage(
            applicationContext = androidApplication(),
            dispatcher = get(IoDispatcherQualifier)
        )
    }
}