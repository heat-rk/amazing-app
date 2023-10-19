package ru.heatalways.amazingapplication.feature.facts.compose_impl.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.amazingapplication.core.data.translator.Translator
import ru.heatalways.amazingapplication.feature.facts.api.domain.FactsRepository
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
            Log.e("FactsRepositoryImpl", e.stackTraceToString())

            factsService.getRandomFact().fact?.let { text ->
                translator.translate(text)
            } ?: throw IOException()
        }
    }
}