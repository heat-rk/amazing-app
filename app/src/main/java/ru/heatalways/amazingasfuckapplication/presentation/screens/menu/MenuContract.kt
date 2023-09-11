package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import kotlinx.collections.immutable.ImmutableList

object MenuContract {
    data class ViewState(
        val items: ImmutableList<MenuItem>
    )
}