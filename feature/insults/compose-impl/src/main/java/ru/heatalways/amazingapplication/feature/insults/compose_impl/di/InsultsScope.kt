package ru.heatalways.amazingapplication.feature.insults.compose_impl.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoCoroutineDispatcher
import ru.heatalways.amazingapplication.feature.insults.api.domain.InsultsRepository
import ru.heatalways.amazingapplication.feature.insults.compose_impl.data.InsultsApiService
import ru.heatalways.amazingapplication.feature.insults.compose_impl.data.InsultsRepositoryImpl
import ru.heatalways.amazingapplication.feature.insults.compose_impl.ui.InsultsViewModel
import scout.Scope
import scout.scope

private var _insultsScope: Scope? = null
internal val insultsScope get() = requireNotNull(_insultsScope)

fun Scope.includeInsultsScope() {
    _insultsScope = scope("insults_scope") {
        dependsOn(this@includeInsultsScope)

        singleton<InsultsApiService> {
            InsultsApiService(
                httpClient = get(),
            )
        }

        singleton<InsultsViewModelFactory> {
            InsultsViewModelFactory(
                viewModelFactory {
                    initializer {
                        InsultsViewModel(
                            router = get(),
                            insultsRepository = get(),
                            sharing = get(),
                        )
                    }
                }
            )
        }

        reusable<InsultsRepository> {
            InsultsRepositoryImpl(
                insultsService = get(),
                dispatcher = get<IoCoroutineDispatcher>().instance,
            )
        }
    }
}