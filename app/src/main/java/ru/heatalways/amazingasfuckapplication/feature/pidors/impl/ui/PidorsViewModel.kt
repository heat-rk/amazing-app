package ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.common.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.common.utils.ifInstance
import ru.heatalways.amazingasfuckapplication.common.utils.launchSafe
import ru.heatalways.amazingasfuckapplication.common.utils.strRes
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.feature.pidors.api.domain.Pidor
import ru.heatalways.amazingasfuckapplication.feature.pidors.api.domain.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toUIListItem
import ru.heatalways.amazingasfuckapplication.feature.pidors.api.navigation.PidorEditScreenRoute
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui.PidorsContract.SideEffect
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui.PidorsContract.ViewState
import ru.heatalways.amazingasfuckapplication.core.design.R as DesignR

class PidorsViewModel(
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
) : ViewModel(), ContainerHost<ViewState, SideEffect> {

    override val container = container<ViewState, SideEffect>(
        initialState = ViewState.Loading
    )

    init {
        intent {
            initItemsObserving()
        }
    }

    fun onReloadClick() = intent {
        initItemsObserving()
    }

    fun onCreateClick() = intent {
        router.navigate(PidorEditScreenRoute())
    }

    fun onEditClick() = intent {
        state.ifInstance<ViewState.Ok> { state ->
            val itemToEdit = state.items.find { it.isSelected } ?: return@intent
            val avatarPath = (itemToEdit.avatar as? PainterResource.ByFile)?.file?.path ?: return@intent

            router.navigate(
                PidorEditScreenRoute(
                    id = itemToEdit.id,
                    name = itemToEdit.name,
                    photoPath = avatarPath
                )
            )
        }
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
                    postSideEffect(SideEffect.Message(strRes(DesignR.string.error_ramil_blame)))
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
                    postSideEffect(SideEffect.Message(strRes(DesignR.string.error_ramil_blame)))
                }
            )
        }
    }

    fun onItemLongClick(item: PidorItem) = intent {
        changeItemSelectedState(item)
    }

    private suspend fun SimpleSyntax<ViewState, SideEffect>.initItemsObserving() {
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
                reduce { ViewState.Error(strRes(DesignR.string.error_ramil_blame)) }
            }
        )
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