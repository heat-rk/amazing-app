package ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.ui

import ru.heatalways.amazingasfuckapplication.core.composables.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.feature.cats.api.domain.CatsRepository
import ru.heatalways.amazingasfuckapplication.feature.sharing.api.Sharing

class CatsViewModel(
    router: Router,
    private val catsRepository: CatsRepository,
    private val sharing: Sharing,
) : PagerViewModel<String>(
    router = router,
    pageLoadOffset = 3
) {
    init {
        load(initialLoading = true)
    }

    override suspend fun load(offset: Int, limit: Int): List<String> {
        return listOf(catsRepository.getRandomCatGifUrl())
    }

    override suspend fun share(item: String) {
        sharing.shareLink(item)
    }
}