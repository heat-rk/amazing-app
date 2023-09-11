package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import kotlinx.collections.immutable.ImmutableList

object PidorsContract {
    sealed interface ViewState {
        object Loading : ViewState

        data class Ok(
            val items: ImmutableList<PidorItem>,
        ) : ViewState
    }
}