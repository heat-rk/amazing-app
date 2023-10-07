package ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui.edit

import ru.heatalways.amazingasfuckapplication.common.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.common.utils.StringResource
import ru.heatalways.amazingasfuckapplication.common.utils.isNotEmpty

object PidorEditContract {

    data class ViewState(
        val name: String,
        val avatar: PainterResource,
        val isEdition: Boolean,
    ) {
        val canBeSaved get() =
            name.isNotBlank() &&
            avatar.isNotEmpty()
    }

    sealed interface SideEffect {
        data class Message(val message: StringResource) : SideEffect
    }
}