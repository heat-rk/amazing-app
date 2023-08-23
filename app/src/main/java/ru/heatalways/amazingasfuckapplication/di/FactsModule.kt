package ru.heatalways.amazingasfuckapplication.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.facts.FactsApiService
import ru.heatalways.amazingasfuckapplication.data.facts.FactsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.domain.facts.FactsRepository

val factsModule = module {
    factoryOf(::FactsApiService)

    factory<FactsRepository> {
        FactsRepositoryImpl(
            factsService = get(),
            dispatcher = get(IoDispatcherQualifier)
        )
    }
}