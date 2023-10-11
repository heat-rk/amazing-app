package ru.heatalways.amazingapplication.feature.insults.api.domain

interface InsultsRepository {
    suspend fun getRandomInsult(): String
}