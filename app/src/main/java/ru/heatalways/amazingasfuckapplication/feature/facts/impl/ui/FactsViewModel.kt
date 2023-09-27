package ru.heatalways.amazingasfuckapplication.feature.facts.impl.ui

import ru.heatalways.amazingasfuckapplication.common.utils.StringResource
import ru.heatalways.amazingasfuckapplication.common.utils.strRes
import ru.heatalways.amazingasfuckapplication.core.composables.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.feature.facts.api.domain.FactsRepository
import ru.heatalways.amazingasfuckapplication.feature.sharing.api.Sharing

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