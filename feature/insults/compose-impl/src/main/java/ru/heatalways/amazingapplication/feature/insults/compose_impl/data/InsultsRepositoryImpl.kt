package ru.heatalways.amazingapplication.feature.insults.compose_impl.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingapplication.feature.insults.api.domain.InsultsRepository
import ru.heatalways.amazingapplication.feature.insults.compose_impl.data.InsultsApiService
import java.io.IOException

class InsultsRepositoryImpl(
    private val insultsService: InsultsApiService,
    private val dispatcher: CoroutineDispatcher,
) : InsultsRepository {
    override suspend fun getRandomInsult() = withContext(dispatcher) {
        insultsService.getRandomInsult().insult ?: throw IOException()
    }
}