package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import kotlinx.collections.immutable.ImmutableList
import ru.heatalways.amazingasfuckapplication.utils.StringResource

object PagerContract {
    sealed interface ViewState<T> {
        class Loading<T> : ViewState<T>
        data class Ok<T>(val items: ImmutableList<T>) : ViewState<T>
        data class Error<T>(val message: StringResource) : ViewState<T>
    }

    sealed interface Intent {
        object OnScrollToEnd : Intent
        object OnNavigationButtonClick : Intent
        object OnShowNextButtonClick : Intent
        object OnReloadButtonClick : Intent
    }
}