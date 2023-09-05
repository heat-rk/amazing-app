package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import kotlinx.collections.immutable.ImmutableList

object PidorsContract {
    sealed interface Intent {
        data class OnItemLongClick(val item: PidorItem) : Intent
        data class OnItemClick(val item: PidorItem) : Intent
        object OnDeleteClick : Intent
        object OnEditClick : Intent
        object OnCreateClick : Intent
        object OnNavigationButtonClick : Intent
    }

    sealed interface ViewState {
        object Loading : ViewState

        data class Ok(
            val items: ImmutableList<PidorItem>,
        ) : ViewState
    }
}