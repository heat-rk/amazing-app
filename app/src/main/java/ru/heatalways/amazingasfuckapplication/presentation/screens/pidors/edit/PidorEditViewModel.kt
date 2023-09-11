package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.ViewState
import ru.heatalways.amazingasfuckapplication.utils.PainterResource

class PidorEditViewModel(
    name: String = "",
    photoPath: String = "",
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
) : ViewModel(), ContainerHost<ViewState, Unit> {

    override val container = container<ViewState, Unit>(
        initialState = ViewState(
            name = name,
            avatar = PainterResource.ByPath(photoPath)
        )
    )

    fun onNameChange(value: String) = intent {
        reduce { state.copy(name = value) }
    }

    fun onAvatarPathChanged(value: String) = intent {
        reduce { state.copy(avatar = PainterResource.ByPath(value)) }
    }

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }

    fun onSaveClick() = intent {
        val currentState = state

        if (currentState.avatar !is PainterResource.ByPath) {
            return@intent
        }

        pidorsRepository.create(
            name = currentState.name,
            avatarPath = currentState.avatar.path
        )
    }
}