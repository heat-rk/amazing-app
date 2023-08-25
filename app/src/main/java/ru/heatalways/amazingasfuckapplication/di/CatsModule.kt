package ru.heatalways.amazingasfuckapplication.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.cats.CatsApiService
import ru.heatalways.amazingasfuckapplication.data.cats.CatsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.domain.cats.CatsRepository

val catsModule = module {
    singleOf(::CatsApiService)

    factory<CatsRepository> {
        CatsRepositoryImpl(
            catsService = get(),
            dispatcher = get(IoDispatcherQualifier),
        )
    }
}