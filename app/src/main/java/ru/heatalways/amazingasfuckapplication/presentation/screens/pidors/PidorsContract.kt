package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.heatalways.amazingasfuckapplication.utils.StringResource

object PidorsContract {
    sealed interface ViewState {
        object Loading : ViewState

        data class Ok(
            val items: ImmutableList<PidorItem>,
        ) : ViewState {
            fun withChangedSelectionFor(item: PidorItem) = copy(
                items = items.map {
                    if (item.id == it.id) {
                        it.copy(isSelected = !it.isSelected)
                    } else {
                        it
                    }
                }.toImmutableList()
            )
        }

        data class Error(val message: StringResource) : ViewState
    }

    sealed interface SideEffect {
        data class Message(val message: StringResource) : SideEffect
    }
}