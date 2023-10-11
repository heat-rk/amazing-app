package ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingasfuckapplication.feature.cats.api.domain.CatsRepository
import ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.data.CatsApiService
import ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.data.CatsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.ui.CatsViewModel

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