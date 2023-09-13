package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import ru.heatalways.amazingasfuckapplication.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.isNotEmpty

object PidorEditContract {

    data class ViewState(
        val name: String,
        val avatar: PainterResource,
    ) {
        val canBeSaved get() =
            name.isNotBlank() &&
            avatar.isNotEmpty()
    }

    sealed interface SideEffect {
        data class Message(val message: StringResource) : SideEffect
    }
}