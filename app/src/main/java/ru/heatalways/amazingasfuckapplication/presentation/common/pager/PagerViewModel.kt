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
    protected val router: Router
) : MviViewModel<ViewState<T>, Intent>(
    initialState = ViewState.Loading()
) {
    private val loadingLock = Mutex()

    init {
        load()
    }

    abstract suspend fun load(offset: Int, limit: Int): List<T>

    override fun onNewIntent(intent: Intent) {
        when (intent) {
            Intent.OnNavigationButtonClick -> navigateBack()
            Intent.OnScrollToEnd -> load()
            Intent.OnShowNextButtonClick -> Unit
            Intent.OnReloadButtonClick -> Unit
        }
    }

    private fun load() {
        viewModelScope.launchSafe(
            block = {
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

    companion object {
        private const val PAGER_LIMIT = 10
    }
}