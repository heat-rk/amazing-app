package ru.heatalways.amazingasfuckapplication.data.cats

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.BuildConfig
import ru.heatalways.amazingasfuckapplication.domain.cats.CatsRepository
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