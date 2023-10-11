package ru.heatalways.amazingasfuckapplication.feature.cats.api.domain

interface CatsRepository {
    suspend fun getRandomCatGifUrl(): String
}