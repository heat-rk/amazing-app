package ru.heatalways.amazingapplication.feature.facts.compose_impl.ui

import ru.heatalways.amazingapplication.common.utils.StringResource
import ru.heatalways.amazingapplication.common.utils.strRes
import ru.heatalways.amazingapplication.core.composables.pager.PagerViewModel
import ru.heatalways.amazingapplication.core.navigation.api.Router
import ru.heatalways.amazingapplication.feature.facts.api.domain.FactsRepository
import ru.heatalways.amazingapplication.feature.sharing.api.Sharing

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