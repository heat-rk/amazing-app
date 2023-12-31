package ru.heatalways.amazingapplication.feature.facts.compose_impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import ru.heatalways.amazingapplication.feature.facts.compose_impl.BuildConfig

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

    suspend fun getRandomFact(): FactDTO =
        httpClient.get("${BuildConfig.FACTS_API_BASE_URL}/facts") {
            url {
                parameters.append("limit", "1")
            }

            headers {
                append(HEADER_X_API_KEY, API_KEY)
            }
        }.body<List<FactDTO>>().first()

    companion object {
        private const val API_KEY = "Z7aFcqM0fnfHs7WpFAgk6Q==dDhqZu14oCEAaJ5w"
        private const val HEADER_X_API_KEY = "X-Api-Key"
    }
}