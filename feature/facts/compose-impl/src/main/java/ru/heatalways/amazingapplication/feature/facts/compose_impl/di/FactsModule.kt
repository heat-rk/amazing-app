package ru.heatalways.amazingapplication.feature.facts.compose_impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingapplication.core.data.translator.di.EnglishToRuTranslatorQualifier
import ru.heatalways.amazingapplication.feature.facts.api.domain.FactsRepository
import ru.heatalways.amazingapplication.feature.facts.compose_impl.data.FactsApiService
import ru.heatalways.amazingapplication.feature.facts.compose_impl.data.FactsParserService
import ru.heatalways.amazingapplication.feature.facts.compose_impl.data.FactsRepositoryImpl
import ru.heatalways.amazingapplication.feature.facts.compose_impl.ui.FactsViewModel

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