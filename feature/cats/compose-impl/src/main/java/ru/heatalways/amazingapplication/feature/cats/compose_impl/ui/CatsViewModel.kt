package ru.heatalways.amazingapplication.feature.cats.compose_impl.ui

import ru.heatalways.amazingapplication.core.composables.pager.PagerViewModel
import ru.heatalways.amazingapplication.core.navigation.api.Router
import ru.heatalways.amazingapplication.feature.cats.api.domain.CatsRepository
import ru.heatalways.amazingapplication.feature.sharing.api.Sharing

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