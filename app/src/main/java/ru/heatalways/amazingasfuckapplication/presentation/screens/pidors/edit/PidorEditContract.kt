package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import ru.heatalways.amazingasfuckapplication.utils.PainterResource

object PidorEditContract {
    sealed interface Intent {
        data class OnNameChange(val value: String) : Intent
        data class OnAvatarPathChanged(val value: String) : Intent
        object OnSaveClick : Intent
        object OnNavigationButtonClick : Intent
    }

    data class ViewState(
        val name: String,
        val avatar: PainterResource,
    ) {
        val canBeSaved get() =
            name.isNotBlank() &&
            avatar is PainterResource.ByPath && avatar.path.isNotBlank()
    }
}