package ru.heatalways.amazingasfuckapplication.presentation.screens.insults

import ru.heatalways.amazingasfuckapplication.common.utils.StringResource
import ru.heatalways.amazingasfuckapplication.common.utils.strRes
import ru.heatalways.amazingasfuckapplication.domain.insults.InsultsRepository
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.common.pager.PagerViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.sharing.api.Sharing

class InsultsViewModel(
    router: Router,
    private val insultsRepository: InsultsRepository,
    private val sharing: Sharing,
) : PagerViewModel<StringResource>(
    router = router,
    pageLoadOffset = 1
) {
    init {
        load(initialLoading = true)
    }

    override suspend fun load(offset: Int, limit: Int): List<StringResource> {
        return listOf(strRes(insultsRepository.getRandomInsult()))
    }

    override suspend fun share(item: StringResource) {
        sharing.shareText(item)
    }
}