package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui

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
import ru.heatalways.amazingapplication.common.utils.PainterResource
import ru.heatalways.amazingapplication.common.utils.ifInstance
import ru.heatalways.amazingapplication.common.utils.launchSafe
import ru.heatalways.amazingapplication.common.utils.strRes
import ru.heatalways.amazingapplication.core.navigation.api.Router
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopUnit
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopRepository
import ru.heatalways.amazingapplication.feature.hate_top.api.navigation.HateTopUnitEditScreenRoute
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.mappers.toUIListItem
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopContract.SideEffect
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopContract.ViewState
import ru.heatalways.amazingapplication.core.design.R as DesignR

class HateTopViewModel(
    private val router: Router,
    private val hateTopRepository: HateTopRepository,
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
        router.navigate(HateTopUnitEditScreenRoute())
    }

    fun onEditClick() = intent {
        state.ifInstance<ViewState.Ok> { state ->
            val itemToEdit = state.items.find { it.isSelected } ?: return@intent
            val avatarPath = (itemToEdit.avatar as? PainterResource.ByFile)?.file?.path ?: return@intent

            router.navigate(
                HateTopUnitEditScreenRoute(
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
                    hateTopRepository.delete(
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

    fun onItemClick(item: HateTopItem) = intent {
        state.ifInstance<ViewState.Ok> { state ->
            if (state.items.any { it.isSelected }) {
                changeItemSelectedState(item)
                return@intent
            }

            viewModelScope.launchSafe(
                block = {
                    hateTopRepository.update(
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

    fun onItemLongClick(item: HateTopItem) = intent {
        changeItemSelectedState(item)
    }

    private suspend fun SimpleSyntax<ViewState, SideEffect>.initItemsObserving() {
        reduce { ViewState.Loading }

        viewModelScope.launchSafe(
            block = {
                hateTopRepository.observeAllSorted()
                    .map(List<HateTopUnit>::toUIListItem)
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
        item: HateTopItem
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