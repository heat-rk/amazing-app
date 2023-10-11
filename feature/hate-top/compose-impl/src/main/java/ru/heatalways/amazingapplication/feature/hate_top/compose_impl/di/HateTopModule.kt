package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingapplication.core.coroutines.scopes.di.LongRunningCoroutineScopeQualifier
import ru.heatalways.amazingapplication.core.data.cache.InMemoryCacheContainer
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopRepository
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data.HateTopRepositoryImpl
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data.storage.HateTopAvatarsStorage
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopViewModel
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditViewModel

private val internalHateTopModule = module {
    single {
        InMemoryCacheContainer<List<HateTopEntity>>(
            cacheLifeTime = Long.MAX_VALUE
        )
    }

    single {
        HateTopAvatarsStorage(
            applicationContext = androidApplication()
        )
    }

    viewModelOf(::HateTopViewModel)

    viewModel { params ->
        HateTopUnitEditViewModel(
            id = params.get(),
            name = params.get(),
            photoPath = params.get(),
            router = get(),
            hateTopRepository = get(),
            tempFilesStorage = get(),
            uriToFileSaver = get(),
            longRunningScope = get(LongRunningCoroutineScopeQualifier)
        )
    }
}

val hateTopModule = module {
    includes(internalHateTopModule)

    factory<HateTopRepository> {
        HateTopRepositoryImpl(
            dispatcher = get(IoDispatcherQualifier),
            hateTopDao = get(),
            inMemoryCacheDataSource = get(),
            longRunningScope = get(LongRunningCoroutineScopeQualifier),
            avatarsStorage = get(),
        )
    }
}