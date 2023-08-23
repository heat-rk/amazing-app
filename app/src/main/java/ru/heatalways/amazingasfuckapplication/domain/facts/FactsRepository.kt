package ru.heatalways.amazingasfuckapplication.domain.facts

interface FactsRepository {
    suspend fun getRandomFacts(limit: Int): List<String>
}