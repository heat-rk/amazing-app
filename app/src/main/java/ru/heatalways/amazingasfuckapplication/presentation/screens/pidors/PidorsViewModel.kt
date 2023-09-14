package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.domain.pidors.Pidor
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toUIListItem
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.SideEffect
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditScreen
import ru.heatalways.amazingasfuckapplication.utils.ifInstance
import ru.heatalways.amazingasfuckapplication.utils.launchSafe
import ru.heatalways.amazingasfuckapplication.utils.strRes

class PidorsViewModel(
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
) : ViewModel(), ContainerHost<ViewState, SideEffect> {

    override val container = container<ViewState, SideEffect>(
        initialState = ViewState.Loading
    )

    init {
        intent {
            reduce { ViewState.Loading }

            viewModelScope.launchSafe(
                block = {
                    pidorsRepository.observeAllSorted()
                        .map(List<Pidor>::toUIListItem)
                        .onEach { items ->
                            reduce { ViewState.Ok(items = items.toImmutableList()) }
                        }
                        .launchIn(this)
                },
                onError = {

                }
            )
        }
    }

    fun onCreateClick() = intent {
        router.navigate(
            route = PidorEditScreen.Route,
            args = mapOf(
                PidorEditScreen.NAME_PARAM to "",
                PidorEditScreen.PHOTO_PATH to "",
            )
        )
    }

    fun onEditClick() = intent {

    }

    fun onDeleteClick() = intent {
        state.ifInstance<ViewState.Ok> { state ->
            viewModelScope.launchSafe(
                block = {
                    pidorsRepository.delete(
                        ids = state.items.mapNotNull { item ->
                            if (item.isSelected) item.id else null
                        }
                    )
                },
                onError = {
                    postSideEffect(SideEffect.Message(strRes(R.string.error_ramil_blame)))
                }
            )
        }
    }

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }

    fun onItemClick(item: PidorItem) = intent {
        state.ifInstance<ViewState.Ok> { state ->
            if (state.items.any { it.isSelected }) {
                changeItemSelectedState(item)
                return@intent
            }

            viewModelScope.launchSafe(
                block = {
                    pidorsRepository.update(
                        id = item.id,
                        tapCount = item.tapCount + 1
                    )
                },
                onError = {
                    postSideEffect(SideEffect.Message(strRes(R.string.error_ramil_blame)))
                }
            )
        }
    }

    fun onItemLongClick(item: PidorItem) = intent {
        changeItemSelectedState(item)
    }

    private suspend fun SimpleSyntax<ViewState, SideEffect>.changeItemSelectedState(
        item: PidorItem
    ) {
        reduce {
            state.let { state ->
                if (state is ViewState.Ok) {
                    state.withChangedSelectionFor(item)
                } else {
                    state
                }
            }
        }
    }
}