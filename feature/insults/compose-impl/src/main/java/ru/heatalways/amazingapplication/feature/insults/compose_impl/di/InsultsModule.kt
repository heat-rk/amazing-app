package ru.heatalways.amazingapplication.feature.insults.compose_impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingapplication.feature.insults.api.domain.InsultsRepository
import ru.heatalways.amazingapplication.feature.insults.compose_impl.data.InsultsApiService
import ru.heatalways.amazingapplication.feature.insults.compose_impl.data.InsultsRepositoryImpl
import ru.heatalways.amazingapplication.feature.insults.compose_impl.ui.InsultsViewModel

private val internalInsultsModule = module {
    factoryOf(::InsultsApiService)
    viewModelOf(::InsultsViewModel)
}

val insultsModule = module {
    includes(internalInsultsModule)

    factory<InsultsRepository> {
        InsultsRepositoryImpl(
            insultsService = get(),
            dispatcher = get(IoDispatcherQualifier),
        )
    }
}