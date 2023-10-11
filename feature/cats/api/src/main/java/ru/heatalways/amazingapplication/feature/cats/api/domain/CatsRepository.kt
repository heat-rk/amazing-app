package ru.heatalways.amazingapplication.feature.cats.api.domain

interface CatsRepository {
    suspend fun getRandomCatGifUrl(): String
}