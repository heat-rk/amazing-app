package ru.heatalways.amazingapplication.core.data.temp_files.di

import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoCoroutineDispatcher
import ru.heatalways.amazingapplication.core.data.temp_files.TempFilesStorage
import scout.definition.Registry

fun Registry.useTempFilesStorageBeans() {
    factory<TempFilesStorage> {
        TempFilesStorage(
            applicationContext = get(),
            dispatcher = get<IoCoroutineDispatcher>().instance
        )
    }
}