package ru.heatalways.amazingasfuckapplication.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.insults.InsultsApiService
import ru.heatalways.amazingasfuckapplication.data.insults.InsultsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.domain.insults.InsultsRepository

val insultsModule = module {
    factoryOf(::InsultsApiService)

    factory<InsultsRepository> {
        InsultsRepositoryImpl(
            insultsService = get(),
            dispatcher = get(IoDispatcherQualifier),
        )
    }
}