package ru.heatalways.amazingasfuckapplication.feature.pidors.compose_impl.ui

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.heatalways.amazingasfuckapplication.common.utils.StringResource

object PidorsContract {
    enum class StateKey {
        LOADING, OK_NOT_EMPTY, OK_EMPTY, ERROR
    }

    sealed interface ViewState {
        val key get() = when (this) {
            is Error -> {
                StateKey.ERROR
            }
            Loading -> {
                StateKey.LOADING
            }
            is Ok -> {
                if (items.isEmpty()) {
                    StateKey.OK_EMPTY
                } else {
                    StateKey.OK_NOT_EMPTY
                }
            }
        }

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