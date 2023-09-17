package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.data.common.temp_files.TempFilesStorage
import ru.heatalways.amazingasfuckapplication.data.common.utils.UriToFileSaver
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.SideEffect
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.ViewState
import ru.heatalways.amazingasfuckapplication.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.utils.launchSafe
import ru.heatalways.amazingasfuckapplication.utils.strRes
import java.io.File

class PidorEditViewModel(
    private val id: Long = -1,
    name: String = "",
    photoPath: String = "",
    private val router: Router,
    private val pidorsRepository: PidorsRepository,
    private val tempFilesStorage: TempFilesStorage,
    private val uriToFileSaver: UriToFileSaver,
) : ViewModel(), ContainerHost<ViewState, SideEffect> {

    override val container = container<ViewState, SideEffect>(
        initialState = ViewState(
            name = name,
            avatar = PainterResource.ByFile(File(photoPath))
        )
    )

    override fun onCleared() {
        intent { tempFilesStorage.clear() }
        super.onCleared()
    }

    fun onNameChange(value: String) = intent {
        reduce { state.copy(name = value) }
    }

    fun onAvatarChanged(uri: Uri) = intent {
        tempFilesStorage.clear()
        val file = tempFilesStorage.new()
        uriToFileSaver.save(uri, destination = file)
        reduce { state.copy(avatar = PainterResource.ByFile(file)) }
    }

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }

    fun onSaveClick() = intent {
        val currentState = state

        if (currentState.avatar !is PainterResource.ByFile) {
            postSideEffect(SideEffect.Message(strRes(R.string.error_ramil_blame)))
            return@intent
        }

        viewModelScope.launchSafe(
            block = {
                pidorsRepository.create(
                    name = currentState.name,
                    avatarFile = currentState.avatar.file,
                )

                router.navigateBack()
            },
            onError = { handleError() }
        )
    }

    private suspend fun SimpleSyntax<ViewState, SideEffect>.handleError() {
        postSideEffect(SideEffect.Message(strRes(R.string.error_ramil_blame)))
    }
}