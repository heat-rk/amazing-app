package ru.heatalways.amazingasfuckapplication.presentation.screens.menu

import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.core.design.R as DesignR
import ru.heatalways.amazingasfuckapplication.common.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.common.utils.StringResource
import ru.heatalways.amazingasfuckapplication.common.utils.painterRes
import ru.heatalways.amazingasfuckapplication.common.utils.strRes

enum class MenuItem(
    val id: String,
    val title: StringResource,
    val icon: PainterResource,
) {
    MIRROR(
        id = "mirror",
        title = strRes(R.string.menu_item_mirror),
        icon = painterRes(DesignR.drawable.icon_mirror)
    ),
    CATS(
        id = "cats",
        title = strRes(R.string.menu_item_cats),
        icon = painterRes(DesignR.drawable.icon_cat)
    ),
    FACTS(
        id = "facts",
        title = strRes(R.string.menu_item_facts),
        icon = painterRes(DesignR.drawable.icon_boobs)
    ),
    INSULTS(
        id = "insults",
        title = strRes(R.string.menu_item_insults),
        icon = painterRes(DesignR.drawable.icon_insult)
    ),
    PIDORS_LIST(
        id = "pidors_list",
        title = strRes(R.string.menu_item_pidors_list),
        icon = painterRes(DesignR.drawable.icon_leaderboard)
    ),
    REMEMBER(
        id = "remember",
        title = strRes(R.string.menu_item_remember),
        icon = painterRes(DesignR.drawable.icon_light)
    )
}
