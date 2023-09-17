package ru.heatalways.amazingasfuckapplication.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.presentation.screens.facts.FactsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.menu.MenuViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.insults.InsultsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.cats.CatsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditViewModel
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.RememberViewModel

val viewModelsModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::FactsViewModel)
    viewModelOf(::InsultsViewModel)
    viewModelOf(::CatsViewModel)
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