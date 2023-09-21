package ru.heatalways.amazingasfuckapplication.presentation.common.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.common.utils.ifInstance
import ru.heatalways.amazingasfuckapplication.common.utils.launchSafe
import ru.heatalways.amazingasfuckapplication.common.utils.strRes
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.SideEffect
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerContract.ViewState

abstract class PagerViewModel<T>(
    protected val router: Router,
    private val pageLoadOffset: Int,
) : ViewModel(), ContainerHost<ViewState<T>, SideEffect> {

    override val container = container<ViewState<T>, SideEffect>(
        initialState = ViewState.Loading()
    )

    private val loadingLock = Mutex()

    protected abstract suspend fun load(offset: Int, limit: Int): List<T>

    protected abstract suspend fun share(item: T)

    fun onReloadButtonClick() = intent {
        load(initialLoading = true)
    }

    fun onNavigateBack() = intent {
        router.navigateBack()
    }

    fun onPageSelected(page: Int) = intent {
        state.ifInstance<ViewState.Ok<T>> { currentState ->
            reduce { currentState.copy(currentPage = page) }

            if (page >= currentState.items.size - pageLoadOffset) {
                load()
            }
        }
    }

    fun onShareClick() = intent {
        state.ifInstance<ViewState.Ok<T>> { currentState ->
            viewModelScope.launchSafe(
                block = {
                    share(currentState.items[currentState.currentPage])
                },
                onError = {
                    postSideEffect(SideEffect.Message(strRes(R.string.error_ramil_blame)))
                }
            )
        }
    }

    protected fun load(initialLoading: Boolean = false) = intent {
        viewModelScope.launchSafe(
            block = {
                loadingLock.withLock {
                    val currentState = if (initialLoading) {
                        ViewState.Loading<T>().also { reduce { it } }
                    } else {
                        container.stateFlow.value
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

    companion object {
        private const val PAGER_LIMIT = 10
    }
}