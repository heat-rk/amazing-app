package ru.heatalways.amazingasfuckapplication.feature.insults.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingasfuckapplication.feature.insults.api.domain.InsultsRepository
import ru.heatalways.amazingasfuckapplication.feature.insults.impl.data.InsultsApiService
import ru.heatalways.amazingasfuckapplication.feature.insults.impl.data.InsultsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.feature.insults.impl.ui.InsultsViewModel

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