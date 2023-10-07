package ru.heatalways.amazingasfuckapplication.feature.menu.impl.ui

import kotlinx.collections.immutable.ImmutableList

object MenuContract {
    data class ViewState(
        val items: ImmutableList<MenuItem>
    )
}