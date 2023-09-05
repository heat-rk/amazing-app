package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toUIListItem
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditScreen

class PidorsViewModel(
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
) : MviViewModel<ViewState, Intent>(
    initialState = ViewState.Loading
) {
    init {
        updateList(showLoading = true)
    }

    override fun onNewIntent(intent: Intent) {
        when (intent) {
            Intent.OnNavigationButtonClick -> onNavigationButtonClick()
            Intent.OnCreateClick -> onCreateClick()
            Intent.OnDeleteClick -> TODO()
            Intent.OnEditClick -> TODO()
            is Intent.OnItemClick -> onItemClick(intent.item)
            is Intent.OnItemLongClick -> onItemLongClick(intent.item)
        }
    }

    private fun updateList(showLoading: Boolean = false) {
        if (showLoading) {
            reduce { ViewState.Loading }
        }

        viewModelScope.launch {
            val items = pidorsRepository.getAllSorted().map { it.toUIListItem() }
            reduce { ViewState.Ok(items = items.toImmutableList()) }
        }
    }

    private fun onCreateClick() {
        viewModelScope.launch {
            router.navigate(ScreenRoute(
                definition = PidorEditScreen.RouteDefinition,
                params = mapOf(
                    PidorEditScreen.NAME_PARAM to "",
                    PidorEditScreen.PHOTO_PATH to "",
                )
            ))
        }
    }

    private fun onNavigationButtonClick() {
        viewModelScope.launch {
            router.navigateBack()
        }
    }

    private fun onItemClick(item: PidorItem) {
        viewModelScope.launch {
            pidorsRepository.update(
                id = item.id,
                tapCount = item.tapCount + 1
            )

            updateList()
        }
    }

    private fun onItemLongClick(item: PidorItem) {
        reduce {
            if (this is ViewState.Ok) {
                copy(
                    items = items.map {
                        if (item.id == it.id) {
                            it.copy(isSelected = true)
                        } else {
                            it
                        }
                    }.toImmutableList()
                )
            } else {
                this
            }
        }
    }
}