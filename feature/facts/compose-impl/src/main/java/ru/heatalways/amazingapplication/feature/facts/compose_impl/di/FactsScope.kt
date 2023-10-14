package ru.heatalways.amazingapplication.feature.facts.compose_impl.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoCoroutineDispatcher
import ru.heatalways.amazingapplication.core.data.translator.EnglishToRuTranslator
import ru.heatalways.amazingapplication.feature.facts.api.domain.FactsRepository
import ru.heatalways.amazingapplication.feature.facts.compose_impl.data.FactsApiService
import ru.heatalways.amazingapplication.feature.facts.compose_impl.data.FactsParserService
import ru.heatalways.amazingapplication.feature.facts.compose_impl.data.FactsRepositoryImpl
import ru.heatalways.amazingapplication.feature.facts.compose_impl.ui.FactsViewModel
import scout.Scope
import scout.scope

private var _factsScope: Scope? = null
internal val factsScope get() = requireNotNull(_factsScope)

fun Scope.includeFactsScope() {
    _factsScope = scope("facts_scope") {
        dependsOn(this@includeFactsScope)

        singleton<FactsApiService> {
            FactsApiService(httpClient = get())
        }

        singleton<FactsParserService> { FactsParserService() }

        singleton<FactsViewModelFactory> {
            FactsViewModelFactory(
                viewModelFactory {
                    initializer {
                        FactsViewModel(
                            router = get(),
                            factsRepository = get(),
                            sharing = get(),
                        )
                    }
                }
            )
        }

        reusable<FactsRepository> {
            FactsRepositoryImpl(
                factsService = get(),
                factsParserService = get(),
                translator = get<EnglishToRuTranslator>().instance,
                dispatcher = get<IoCoroutineDispatcher>().instance,
            )
        }
    }
}