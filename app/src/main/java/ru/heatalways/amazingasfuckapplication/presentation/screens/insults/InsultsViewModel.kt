package ru.heatalways.amazingasfuckapplication.presentation.screens.insults

import ru.heatalways.amazingasfuckapplication.domain.insults.InsultsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.strRes

class InsultsViewModel(
    router: Router,
    private val insultsRepository: InsultsRepository,
) : PagerViewModel<StringResource>(
    router = router,
    pageLoadOffset = 1
) {
    init {
        initLoading()
    }

    override suspend fun load(offset: Int, limit: Int): List<StringResource> {
        return listOf(strRes(insultsRepository.getRandomInsult()))
    }
}