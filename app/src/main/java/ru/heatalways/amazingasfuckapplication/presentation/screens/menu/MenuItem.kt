package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.painterRes
import ru.heatalways.amazingasfuckapplication.utils.strRes

enum class MenuItem(
    val id: String,
    val title: StringResource,
    val icon: PainterResource,
) {
    MIRROR(
        id = "mirror",
        title = strRes(R.string.menu_item_mirror),
        icon = painterRes(R.drawable.icon_mirror)
    ),
    CATS(
        id = "cats",
        title = strRes(R.string.menu_item_cats),
        icon = painterRes(R.drawable.icon_cat)
    ),
    FACTS(
        id = "facts",
        title = strRes(R.string.menu_item_facts),
        icon = painterRes(R.drawable.icon_boobs)
    ),
    INSULTS(
        id = "insults",
        title = strRes(R.string.menu_item_insults),
        icon = painterRes(R.drawable.icon_insult)
    ),
    PIDORS_LIST(
        id = "pidors_list",
        title = strRes(R.string.menu_item_pidors_list),
        icon = painterRes(R.drawable.icon_leaderboard)
    )
}
