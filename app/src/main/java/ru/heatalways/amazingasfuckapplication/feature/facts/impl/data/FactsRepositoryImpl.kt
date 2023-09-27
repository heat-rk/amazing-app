package ru.heatalways.amazingasfuckapplication.feature.facts.impl.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.data.translator.Translator
import ru.heatalways.amazingasfuckapplication.feature.facts.api.domain.FactsRepository
import java.io.IOException

class FactsRepositoryImpl(
    private val factsService: FactsApiService,
    private val factsParserService: FactsParserService,
    private val translator: Translator,
    private val dispatcher: CoroutineDispatcher,
) : FactsRepository {
    override suspend fun getRandomFacts(limit: Int) = withContext(dispatcher) {
        factsService.getRandomFacts(limit).mapNotNull {
            it.fact?.let { text -> translator.translate(text) }
        }
    }

    override suspend fun getRandomFact() = withContext(dispatcher) {
        try {
            factsParserService.getRandomFact().fact ?: throw IOException()
        } catch (e: Exception) {
            factsService.getRandomFact().fact ?: throw IOException()
        }
    }
}