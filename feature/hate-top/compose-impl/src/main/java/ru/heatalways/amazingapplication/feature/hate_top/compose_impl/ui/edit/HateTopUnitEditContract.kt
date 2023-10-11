package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit

import ru.heatalways.amazingapplication.common.utils.PainterResource
import ru.heatalways.amazingapplication.common.utils.StringResource
import ru.heatalways.amazingapplication.common.utils.isNotEmpty

object HateTopUnitEditContract {

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