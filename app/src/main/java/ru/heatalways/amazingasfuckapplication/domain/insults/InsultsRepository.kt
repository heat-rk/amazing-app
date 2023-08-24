package ru.heatalways.amazingasfuckapplication.domain.insults

interface InsultsRepository {
    suspend fun getRandomInsult(): String
}