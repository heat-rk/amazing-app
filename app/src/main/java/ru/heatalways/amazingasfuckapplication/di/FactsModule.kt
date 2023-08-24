package ru.heatalways.amazingasfuckapplication.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.facts.FactsApiService
import ru.heatalways.amazingasfuckapplication.data.facts.FactsParserService
import ru.heatalways.amazingasfuckapplication.data.facts.FactsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.domain.facts.FactsRepository

val factsModule = module {
    factoryOf(::FactsApiService)
    factoryOf(::FactsParserService)

    factory<FactsRepository> {
        FactsRepositoryImpl(
            factsService = get(),
            factsParserService = get(),
            dispatcher = get(IoDispatcherQualifier),
            translator = get(EnglishToRuTranslatorQualifier)
        )
    }
}