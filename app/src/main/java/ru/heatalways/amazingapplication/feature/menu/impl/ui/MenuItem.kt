package ru.heatalways.amazingapplication.feature.menu.impl.ui

import ru.heatalways.amazingapplication.R
import ru.heatalways.amazingapplication.core.design.R as DesignR
import ru.heatalways.amazingapplication.common.utils.PainterResource
import ru.heatalways.amazingapplication.common.utils.StringResource
import ru.heatalways.amazingapplication.common.utils.painterRes
import ru.heatalways.amazingapplication.common.utils.strRes

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
    HATE_TOP(
        id = "hate_top",
        title = strRes(R.string.menu_item_hate_top),
        icon = painterRes(DesignR.drawable.icon_leaderboard)
    ),
}
