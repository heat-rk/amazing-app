package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.ViewState
import ru.heatalways.amazingasfuckapplication.utils.launchSafe
import ru.heatalways.amazingasfuckapplication.utils.strRes

abstract class PagerViewModel<T>(
    protected val router: Router,
    private val pageLoadOffset: Int,
) : MviViewModel<ViewState<T>, Intent>(
    initialState = ViewState.Loading()
) {
    private val loadingLock = Mutex()

    protected fun initLoading() {
        load(initialLoading = true)
    }

    abstract suspend fun load(offset: Int, limit: Int): List<T>

    override fun onNewIntent(intent: Intent) {
        when (intent) {
            Intent.OnNavigationButtonClick -> navigateBack()
            Intent.OnReloadButtonClick -> load(initialLoading = true)
            is Intent.OnPageSelected -> onPageSelected(intent.page)
        }
    }

    private fun load(initialLoading: Boolean = false) {
        viewModelScope.launchSafe(
            block = {
                loadingLock.withLock {
                    val currentState = if (initialLoading) {
                        ViewState.Loading<T>().also { reduce { it } }
                    } else {
                        state.value
                    }

                    val offset = if (currentState is ViewState.Ok) {
                        currentState.items.size
                    } else {
                        0
                    }

                    val newItems = load(offset, PAGER_LIMIT)

                    reduce {
                        if (currentState is ViewState.Ok) {
                            currentState.copy(items = (currentState.items + newItems).toImmutableList())
                        } else {
                            ViewState.Ok(newItems.toImmutableList())
                        }
                    }
                }
            },
            onError = {
                reduce {
                    ViewState.Error(strRes(R.string.error_something_went_wrong))
                }
            }
        )
    }

    private fun navigateBack() {
        viewModelScope.launch {
            router.navigateBack()
        }
    }

    private fun onPageSelected(page: Int) {
        val currentState = state.value

        if (currentState is ViewState.Ok && page >= currentState.items.size - pageLoadOffset) {
            load()
        }
    }

    companion object {
        private const val PAGER_LIMIT = 10
    }
}