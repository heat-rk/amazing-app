package ru.heatalways.amazingapplication.feature.cats.compose_impl.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingapplication.feature.cats.compose_impl.BuildConfig
import ru.heatalways.amazingapplication.feature.cats.api.domain.CatsRepository
import java.io.IOException

internal class CatsRepositoryImpl(
    private val catsService: CatsApiService,
    private val dispatcher: CoroutineDispatcher,
) : CatsRepository {
    override suspend fun getRandomCatGifUrl() = withContext(dispatcher) {
        catsService.getRandomCat().url?.let { path ->
            "${BuildConfig.CATS_API_BASE_URL}/$path"
        } ?: throw IOException()
    }
}