package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import ru.heatalways.amazingasfuckapplication.utils.PainterResource

object PidorEditContract {

    data class ViewState(
        val name: String,
        val avatar: PainterResource,
    ) {
        val canBeSaved get() =
            name.isNotBlank() &&
            avatar is PainterResource.ByPath && avatar.path.isNotBlank()
    }
}