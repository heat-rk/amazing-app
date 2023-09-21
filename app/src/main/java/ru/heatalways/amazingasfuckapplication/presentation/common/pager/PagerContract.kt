package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import kotlinx.collections.immutable.ImmutableList
import ru.heatalways.amazingasfuckapplication.utils.StringResource

object PagerContract {
    sealed interface ViewState<T> {
        class Loading<T> : ViewState<T>

        data class Ok<T>(
            val items: ImmutableList<T>,
            val currentPage: Int = 0,
        ) : ViewState<T>

        data class Error<T>(val message: StringResource) : ViewState<T>
    }

    sealed interface SideEffect {
        data class Message(val message: StringResource) : SideEffect
    }
}