package ru.heatalways.amazingapplication.feature.facts.api.domain

interface FactsRepository {
    suspend fun getRandomFacts(limit: Int): List<String>
    suspend fun getRandomFact(): String
}