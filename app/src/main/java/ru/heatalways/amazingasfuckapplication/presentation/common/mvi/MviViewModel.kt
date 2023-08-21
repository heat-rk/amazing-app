package ru.heatalways.amazingasfuckapplication.presentation.common.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class MviViewModel<S, I>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun intent(intent: I) {
        onNewIntent(intent)
    }

    protected abstract fun onNewIntent(intent: I)

    protected fun reduce(reducer: S.() -> S) {
        _state.update(reducer)
    }
}