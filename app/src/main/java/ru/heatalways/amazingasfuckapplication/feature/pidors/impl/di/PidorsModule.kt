package ru.heatalways.amazingasfuckapplication.feature.pidors.impl.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.coroutines.dispatchers.IoDispatcherQualifier
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity
import ru.heatalways.amazingasfuckapplication.data.common.cache.InMemoryCacheContainer
import ru.heatalways.amazingasfuckapplication.di.LongRunningCoroutineScopeQualifier
import ru.heatalways.amazingasfuckapplication.feature.pidors.api.domain.PidorsRepository
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.PidorsRepositoryImpl
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.storage.PidorsAvatarsStorage
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui.PidorsViewModel
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui.edit.PidorEditViewModel

private val internalPidorsModule = module {
    single {
        InMemoryCacheContainer<List<PidorEntity>>(
            cacheLifeTime = Long.MAX_VALUE
        )
    }

    single {
        PidorsAvatarsStorage(
            applicationContext = androidApplication()
        )
    }

    viewModelOf(::PidorsViewModel)

    viewModel { params ->
        PidorEditViewModel(
            id = params.get(),
            name = params.get(),
            photoPath = params.get(),
            router = get(),
            pidorsRepository = get(),
            tempFilesStorage = get(),
            uriToFileSaver = get(),
            longRunningScope = get(LongRunningCoroutineScopeQualifier)
        )
    }
}

val pidorsModule = module {
    includes(internalPidorsModule)

    factory<PidorsRepository> {
        PidorsRepositoryImpl(
            dispatcher = get(IoDispatcherQualifier),
            pidorsDao = get(),
            inMemoryCacheDataSource = get(),
            longRunningScope = get(LongRunningCoroutineScopeQualifier),
            avatarsStorage = get(),
        )
    }
}