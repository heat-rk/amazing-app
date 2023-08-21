package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.ViewState

abstract class PagerViewModel<T> : MviViewModel<ViewState<T>, Intent>(
    initialState = ViewState.Loading()
) {
    private val loadingLock = Mutex()

    init {
        onLoadMore()
    }

    abstract suspend fun load(offset: Int, limit: Int): List<T>

    override fun onNewIntent(intent: Intent) {
        when (intent) {
            Intent.GoBack -> Unit
            Intent.LoadMore -> onLoadMore()
            Intent.ShowNext -> Unit
        }
    }

    private fun onLoadMore() {
        viewModelScope.launch {
            loadingLock.withLock {
                val currentState = state.value

                val offset = if (currentState is ViewState.Ok) {
                    currentState.items.size
                } else {
                    0
                }

                val newItems = load(offset, PAGER_LIMIT)

                reduce {
                    if (this is ViewState.Ok) {
                        copy(items = (items + newItems).toImmutableList())
                    } else {
                        ViewState.Ok(newItems.toImmutableList())
                    }
                }
            }
        }
    }

    companion object {
        private const val PAGER_LIMIT = 10
    }
}