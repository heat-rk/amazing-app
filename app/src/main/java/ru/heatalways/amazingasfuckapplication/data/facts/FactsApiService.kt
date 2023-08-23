package ru.heatalways.amazingasfuckapplication.data.facts

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import ru.heatalways.amazingasfuckapplication.BuildConfig

class FactsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getRandomFacts(limit: Int): List<FactDTO> =
        httpClient.get("${BuildConfig.FACTS_API_BASE_URL}/facts") {
            url {
                parameters.append("limit", limit.toString())
            }

            headers {
                append(HEADER_X_API_KEY, API_KEY)
            }
        }.body()

    companion object {
        private const val API_KEY = "Z7aFcqM0fnfHs7WpFAgk6Q==dDhqZu14oCEAaJ5w"
        private const val HEADER_X_API_KEY = "X-Api-Key"
    }
}