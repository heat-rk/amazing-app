package ru.heatalways.amazingasfuckapplication.feature.facts.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingasfuckapplication.di.EnglishToRuTranslatorQualifier
import ru.heatalways.amazingasfuckapplication.feature.facts.api.domain.FactsRepository
import ru.heatalways.amazingasfuckapplication.feature.facts.impl.data.FactsApiService
import ru.heatalways.amazingasfuckapplication.feature.facts.impl.data.FactsParserService
import ru.heatalways.amazingasfuckapplication.feature.facts.impl.data.FactsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.feature.facts.impl.ui.FactsViewModel

private val internalFactsModule = module {
    factoryOf(::FactsApiService)
    factoryOf(::FactsParserService)
    viewModelOf(::FactsViewModel)
}

val factsModule = module {
    includes(internalFactsModule)

    factory<FactsRepository> {
        FactsRepositoryImpl(
            factsService = get(),
            factsParserService = get(),
            dispatcher = get(IoDispatcherQualifier),
            translator = get(EnglishToRuTranslatorQualifier)
        )
    }
}