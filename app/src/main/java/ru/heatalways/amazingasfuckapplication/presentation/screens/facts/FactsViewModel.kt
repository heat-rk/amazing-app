package ru.heatalways.amazingasfuckapplication.presentation.screens.facts

import ru.heatalways.amazingasfuckapplication.domain.facts.FactsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.strRes

class FactsViewModel(
    router: Router,
    private val factsRepository: FactsRepository
) : PagerViewModel<StringResource>(router) {
    init {
        initLoading()
    }

    override suspend fun load(offset: Int, limit: Int): List<StringResource> {
        return factsRepository.getRandomFacts(limit).map { strRes(it) }
    }
}