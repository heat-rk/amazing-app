package ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.BuildConfig

internal class CatsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getRandomCat(): CatDTO =
        httpClient.get("${BuildConfig.CATS_API_BASE_URL}/cat/gif") {
            url {
                parameters.append("json", "true")
            }
        }.body()
}