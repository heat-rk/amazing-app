package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.utils.painterRes
import ru.heatalways.amazingasfuckapplication.utils.strRes

class MenuViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        MenuViewState(
            items = persistentListOf(
                MenuItem(
                    id = "mirror",
                    title = strRes(R.string.menu_item_mirror),
                    icon = painterRes(R.drawable.icon_mirror)
                ),
                MenuItem(
                    id = "cats",
                    title = strRes(R.string.menu_item_cats),
                    icon = painterRes(R.drawable.icon_cat)
                ),
                MenuItem(
                    id = "facts",
                    title = strRes(R.string.menu_item_facts),
                    icon = painterRes(R.drawable.icon_boobs)
                ),
                MenuItem(
                    id = "insults",
                    title = strRes(R.string.menu_item_insults),
                    icon = painterRes(R.drawable.icon_insult)
                ),
                MenuItem(
                    id = "pidors_list",
                    title = strRes(R.string.menu_item_pidors_list),
                    icon = painterRes(R.drawable.icon_leaderboard)
                ),
            )
        )
    )

    val state = _state.asStateFlow()
}