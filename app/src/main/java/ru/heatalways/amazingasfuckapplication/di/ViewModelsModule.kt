package ru.heatalways.amazingasfuckapplication.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.feature.menu.impl.ui.MenuViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.impl.MirrorViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.impl.PidorsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.impl.edit.PidorEditViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.impl.RememberViewModel

val viewModelsModule = module {
    viewModelOf(::MirrorViewModel)
    viewModelOf(::PidorsViewModel)
    viewModelOf(::RememberViewModel)

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