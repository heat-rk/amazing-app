package ru.heatalways.amazingasfuckapplication.data.insults

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.domain.insults.InsultsRepository
import java.io.IOException

class InsultsRepositoryImpl(
    private val insultsService: InsultsApiService,
    private val dispatcher: CoroutineDispatcher,
) : InsultsRepository {
    override suspend fun getRandomInsult() = withContext(dispatcher) {
        insultsService.getRandomInsult().insult ?: throw IOException()
    }
}