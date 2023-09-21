package ru.heatalways.amazingasfuckapplication.presentation.screens.facts

import ru.heatalways.amazingasfuckapplication.common.utils.StringResource
import ru.heatalways.amazingasfuckapplication.common.utils.strRes
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.domain.facts.FactsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.sharing.api.Sharing

class FactsViewModel(
    router: Router,
    private val factsRepository: FactsRepository,
    private val sharing: Sharing,
) : PagerViewModel<StringResource>(
    router = router,
    pageLoadOffset = 1
) {
    init {
        load(initialLoading = true)
    }

    override suspend fun load(offset: Int, limit: Int): List<StringResource> {
        return listOf(strRes(factsRepository.getRandomFact()))
    }

    override suspend fun share(item: StringResource) {
        sharing.shareText(item)
    }
}