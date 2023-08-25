package ru.heatalways.amazingasfuckapplication.domain.cats

interface CatsRepository {
    suspend fun getRandomCatGifUrl(): String
}