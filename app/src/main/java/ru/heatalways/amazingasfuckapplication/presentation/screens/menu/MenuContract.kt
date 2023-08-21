package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import kotlinx.collections.immutable.ImmutableList

object MenuContract {
    data class ViewState(
        val items: ImmutableList<MenuItem>
    )

    sealed interface Intent {
        data class OnMenuItemClick(val item: MenuItem) : Intent
    }
}