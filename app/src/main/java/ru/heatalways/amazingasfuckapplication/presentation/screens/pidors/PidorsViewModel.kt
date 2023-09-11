package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toUIListItem
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorsContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditScreen

class PidorsViewModel(
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
) : ViewModel(), ContainerHost<ViewState, Unit> {

    override val container = container<ViewState, Unit>(
        initialState = ViewState.Loading
    )

    init {
        updateList(showLoading = true)
    }

    private fun updateList(showLoading: Boolean = false) = intent {
        if (showLoading) {
            reduce { ViewState.Loading }
        }

        viewModelScope.launch {
            val items = pidorsRepository.getAllSorted().map { it.toUIListItem() }
            reduce { ViewState.Ok(items = items.toImmutableList()) }
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

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }

    fun onItemClick(item: PidorItem) = intent {
        pidorsRepository.update(
            id = item.id,
            tapCount = item.tapCount + 1
        )

        updateList()
    }

    fun onItemLongClick(item: PidorItem) = intent {
        reduce {
            val currentState = state

            if (currentState is ViewState.Ok) {
                currentState.copy(
                    items = currentState.items.map {
                        if (item.id == it.id) {
                            it.copy(isSelected = true)
                        } else {
                            it
                        }
                    }.toImmutableList()
                )
            } else {
                currentState
            }
        }
    }
}