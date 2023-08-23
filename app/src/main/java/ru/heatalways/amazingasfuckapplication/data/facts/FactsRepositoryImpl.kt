package ru.heatalways.amazingasfuckapplication.data.facts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.domain.facts.FactsRepository

class FactsRepositoryImpl(
    private val factsService: FactsApiService,
    private val dispatcher: CoroutineDispatcher,
) : FactsRepository {
    override suspend fun getRandomFacts(limit: Int) = withContext(dispatcher) {
        factsService.getRandomFacts(limit).mapNotNull { it.fact }
    }
}