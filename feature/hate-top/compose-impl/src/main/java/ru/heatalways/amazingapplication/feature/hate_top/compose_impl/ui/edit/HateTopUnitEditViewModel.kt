package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit

import android.net.Uri
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingapplication.common.utils.PainterResource
import ru.heatalways.amazingapplication.common.utils.launchSafe
import ru.heatalways.amazingapplication.common.utils.strRes
import ru.heatalways.amazingapplication.core.data.temp_files.TempFilesStorage
import ru.heatalways.amazingapplication.core.data.utils.UriToFileSaver
import ru.heatalways.amazingapplication.core.navigation.api.Router
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopUnitAvatarCrop
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopRepository
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditContract.SideEffect
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditContract.ViewState
import java.io.File
import ru.heatalways.amazingapplication.core.design.R as DesignR

class HateTopUnitEditViewModel(
    private val id: Long = -1,
    name: String = "",
    photoPath: String = "",
    private val router: Router,
    private val hateTopRepository: HateTopRepository,
    private val tempFilesStorage: TempFilesStorage,
    private val uriToFileSaver: UriToFileSaver,
    private val longRunningScope: CoroutineScope,
) : ViewModel(), ContainerHost<ViewState, SideEffect> {

    override val container = container<ViewState, SideEffect>(
        initialState = ViewState(
            name = name,
            avatar = PainterResource.ByFile(File(photoPath)),
            isEdition = id != -1L
        )
    )

    private var avatarCrop: HateTopUnitAvatarCrop = HateTopUnitAvatarCrop.Full

    override fun onCleared() {
        longRunningScope.launch { tempFilesStorage.clear() }
        super.onCleared()
    }

    fun onNameChange(value: String) = intent {
        reduce { state.copy(name = value) }
    }

    fun onAvatarChanged(uri: Uri) = intent {
        val file = tempFilesStorage.new()
        uriToFileSaver.save(uri, destination = file)
        reduce { state.copy(avatar = PainterResource.ByFile(file)) }
    }

    fun onAvatarCropChanged(offset: IntOffset, size: IntSize) = intent {
        avatarCrop = HateTopUnitAvatarCrop.Exactly(
            left = offset.x,
            top = offset.y,
            width = size.width,
            height = size.height,
        )
    }

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }

    fun onSaveClick() = intent {
        val currentState = state

        if (currentState.avatar !is PainterResource.ByFile) {
            postSideEffect(SideEffect.Message(strRes(DesignR.string.error_ramil_blame)))
            return@intent
        }

        viewModelScope.launchSafe(
            block = {
                hateTopRepository.create(
                    id = id,
                    name = currentState.name,
                    avatarFile = currentState.avatar.file,
                    avatarCrop = avatarCrop,
                )

                router.navigateBack()
            },
            onError = { handleError() }
        )
    }

    private suspend fun SimpleSyntax<ViewState, SideEffect>.handleError() {
        postSideEffect(SideEffect.Message(strRes(DesignR.string.error_ramil_blame)))
    }

    object IdKey : CreationExtras.Key<Long>
    object NameKey : CreationExtras.Key<String>
    object PhotoPathKey : CreationExtras.Key<String>

    companion object {
        fun create(
            creationExtras: CreationExtras,
            router: Router,
            hateTopRepository: HateTopRepository,
            tempFilesStorage: TempFilesStorage,
            uriToFileSaver: UriToFileSaver,
            longRunningScope: CoroutineScope,
        ) = HateTopUnitEditViewModel(
            id = creationExtras[IdKey] ?: -1,
            name = creationExtras[NameKey] ?: "",
            photoPath = creationExtras[PhotoPathKey] ?: "",
            router = router,
            hateTopRepository = hateTopRepository,
            tempFilesStorage = tempFilesStorage,
            uriToFileSaver = uriToFileSaver,
            longRunningScope = longRunningScope
        )
    }
}