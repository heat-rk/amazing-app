package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoCoroutineDispatcher
import ru.heatalways.amazingapplication.core.coroutines.scopes.LongRunningCoroutineScope
import ru.heatalways.amazingapplication.core.data.cache.InMemoryCacheContainer
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopRepository
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data.HateTopRepositoryImpl
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data.storage.HateTopAvatarsStorage
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopViewModel
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditViewModel
import scout.Scope
import scout.scope

private var _hateTopScope: Scope? = null
internal val hateTopScope get() = requireNotNull(_hateTopScope)

fun Scope.includeHateTopScope() {
    _hateTopScope = scope("hate_top") {
        dependsOn(this@includeHateTopScope)

        singleton<InMemoryCacheContainer<List<HateTopEntity>>> {
            InMemoryCacheContainer(
                cacheLifeTime = Long.MAX_VALUE,
            )
        }

        singleton<HateTopAvatarsStorage> {
            HateTopAvatarsStorage(
                applicationContext = get(),
            )
        }

        singleton<HateTopViewModelFactory> {
            HateTopViewModelFactory(
                viewModelFactory {
                    initializer {
                        HateTopViewModel(
                            router = get(),
                            hateTopRepository = get(),
                        )
                    }
                }
            )
        }

        singleton<HateTopUnitEditViewModelFactory> {
            HateTopUnitEditViewModelFactory(
                viewModelFactory {
                    initializer {
                        HateTopUnitEditViewModel.create(
                            creationExtras = this,
                            router = get(),
                            hateTopRepository = get(),
                            tempFilesStorage = get(),
                            uriToFileSaver = get(),
                            longRunningScope = get<LongRunningCoroutineScope>().instance,
                        )
                    }
                }
            )
        }

        reusable<HateTopRepository> {
            HateTopRepositoryImpl(
                dispatcher = get<IoCoroutineDispatcher>().instance,
                hateTopDao = get(),
                inMemoryCacheDataSource = get(),
                longRunningScope = get<LongRunningCoroutineScope>().instance,
                avatarsStorage = get(),
            )
        }
    }
}