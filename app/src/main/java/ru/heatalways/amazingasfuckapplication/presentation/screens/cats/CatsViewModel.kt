package ru.heatalways.amazingasfuckapplication.presentation.screens.cats

import ru.heatalways.amazingasfuckapplication.domain.cats.CatsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.sharing.api.Sharing

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