package ru.heatalways.amazingapplication.feature.cats.compose_impl.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoCoroutineDispatcher
import ru.heatalways.amazingapplication.feature.cats.api.domain.CatsRepository
import ru.heatalways.amazingapplication.feature.cats.compose_impl.data.CatsApiService
import ru.heatalways.amazingapplication.feature.cats.compose_impl.data.CatsRepositoryImpl
import ru.heatalways.amazingapplication.feature.cats.compose_impl.ui.CatsViewModel
import scout.Scope
import scout.scope

private var _catsScope: Scope? = null
internal val catsScope get() = requireNotNull(_catsScope)

fun Scope.includeCatsScope() {
    _catsScope = scope("cats_scope") {
        dependsOn(this@includeCatsScope)

        singleton<CatsViewModelFactory> {
            CatsViewModelFactory(
                viewModelFactory {
                    initializer {
                        CatsViewModel(
                            router = get(),
                            catsRepository = get(),
                            sharing = get()
                        )
                    }
                }
            )
        }

        singleton<CatsApiService> {
            CatsApiService(
                httpClient = get()
            )
        }

        reusable<CatsRepository> {
            CatsRepositoryImpl(
                catsService = get(),
                dispatcher = get<IoCoroutineDispatcher>().instance
            )
        }
    }
}