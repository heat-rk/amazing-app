package ru.heatalways.amazingasfuckapplication.data.cats

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.heatalways.amazingasfuckapplication.BuildConfig

class CatsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getRandomCat(): CatDTO =
        httpClient.get("${BuildConfig.CATS_API_BASE_URL}/cat/gif") {
            url {
                parameters.append("json", "true")
            }
        }.body()
}