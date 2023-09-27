package ru.heatalways.amazingasfuckapplication.feature.insults.api.domain

interface InsultsRepository {
    suspend fun getRandomInsult(): String
}