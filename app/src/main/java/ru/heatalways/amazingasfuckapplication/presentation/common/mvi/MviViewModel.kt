package ru.heatalways.amazingasfuckapplication.presentation.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

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
        viewModelScope.launch(reducerThreadContext) {
            _state.update(reducer)
        }
    }

    companion object {
        private val reducerThreadContext =
            newSingleThreadContext("MviViewModelReducerContext")
    }
}