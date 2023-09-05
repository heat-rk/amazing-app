package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.ViewState
import ru.heatalways.amazingasfuckapplication.utils.PainterResource

class PidorEditViewModel(
    name: String = "",
    photoPath: String = "",
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
) : MviViewModel<ViewState, Intent>(
    initialState = ViewState(
        name = name,
        avatar = PainterResource.ByPath(photoPath)
    )
) {
    override fun onNewIntent(intent: Intent) {
        when (intent) {
            is Intent.OnAvatarPathChanged -> onAvatarPathChanged(intent.value)
            is Intent.OnNameChange -> onNameChange(intent.value)
            Intent.OnSaveClick -> onSaveClick()
            Intent.OnNavigationButtonClick -> onNavigationButtonClick()
        }
    }

    private fun onNameChange(value: String) {
        reduce { copy(name = value) }
    }

    private fun onAvatarPathChanged(value: String) {
        reduce { copy(avatar = PainterResource.ByPath(value)) }
    }

    private fun onNavigationButtonClick() {
        viewModelScope.launch {
            router.navigateBack()
        }
    }

    private fun onSaveClick() {
        viewModelScope.launch {
            val currentState = state.value

            if (currentState.avatar !is PainterResource.ByPath) {
                return@launch
            }

            pidorsRepository.create(
                name = currentState.name,
                avatarPath = currentState.avatar.path
            )
        }
    }
}