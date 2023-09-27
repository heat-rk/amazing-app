package ru.heatalways.amazingasfuckapplication.feature.cats.impl.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.BuildConfig
import ru.heatalways.amazingasfuckapplication.feature.cats.api.domain.CatsRepository
import java.io.IOException

class CatsRepositoryImpl(
    private val catsService: CatsApiService,
    private val dispatcher: CoroutineDispatcher,
) : CatsRepository {
    override suspend fun getRandomCatGifUrl() = withContext(dispatcher) {
        catsService.getRandomCat().url?.let { path ->
            "${BuildConfig.CATS_API_BASE_URL}/$path"
        } ?: throw IOException()
    }
}