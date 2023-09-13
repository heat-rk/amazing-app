package ru.heatalways.amazingasfuckapplication.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.common.cache.InMemoryCacheContainer
import ru.heatalways.amazingasfuckapplication.data.common.database.AppDatabase
import ru.heatalways.amazingasfuckapplication.data.pidors.PidorsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.data.pidors.storage.PidorsAvatarsStorage
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository

val pidorsModule = module {
    single {
        InMemoryCacheContainer<List<PidorDAO>>(
            cacheLifeTime = Long.MAX_VALUE
        )
    }

    single {
        PidorsAvatarsStorage(
            applicationContext = androidApplication()
        )
    }

    factory<PidorsRepository> {
        PidorsRepositoryImpl(
            dispatcher = get(IoDispatcherQualifier),
            dbDataSource = get<AppDatabase>().pidors(),
            inMemoryCacheDataSource = get(),
            longRunningScope = get(LongRunningCoroutineScopeQualifier),
            avatarsStorage = get(),
        )
    }
}