package ru.heatalways.amazingasfuckapplication.feature.insults.impl.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.feature.insults.api.domain.InsultsRepository
import java.io.IOException

class InsultsRepositoryImpl(
    private val insultsService: InsultsApiService,
    private val dispatcher: CoroutineDispatcher,
) : InsultsRepository {
    override suspend fun getRandomInsult() = withContext(dispatcher) {
        insultsService.getRandomInsult().insult ?: throw IOException()
    }
}