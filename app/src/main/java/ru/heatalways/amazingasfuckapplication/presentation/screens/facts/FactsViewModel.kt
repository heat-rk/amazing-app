package ru.heatalways.amazingasfuckapplication.presentation.screens.facts

import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.strRes

class FactsViewModel : PagerViewModel<StringResource>() {
    override suspend fun load(offset: Int, limit: Int): List<StringResource> {
        return listOf(strRes("lol 1"), strRes("lol 2"))
    }
}