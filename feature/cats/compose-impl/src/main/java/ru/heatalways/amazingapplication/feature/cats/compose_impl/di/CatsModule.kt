package ru.heatalways.amazingapplication.feature.cats.compose_impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingapplication.feature.cats.api.domain.CatsRepository
import ru.heatalways.amazingapplication.feature.cats.compose_impl.data.CatsApiService
import ru.heatalways.amazingapplication.feature.cats.compose_impl.data.CatsRepositoryImpl
import ru.heatalways.amazingapplication.feature.cats.compose_impl.ui.CatsViewModel

private val internalCatsModule = module {
    singleOf(::CatsApiService)
    viewModelOf(::CatsViewModel)
}

val catsModule = module {
    includes(internalCatsModule)

    factory<CatsRepository> {
        CatsRepositoryImpl(
            catsService = get(),
            dispatcher = get(IoDispatcherQualifier),
        )
    }
}