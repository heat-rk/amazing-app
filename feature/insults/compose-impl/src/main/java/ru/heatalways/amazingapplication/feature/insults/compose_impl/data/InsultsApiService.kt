package ru.heatalways.amazingapplication.feature.insults.compose_impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.heatalways.amazingapplication.feature.insults.compose_impl.BuildConfig

class InsultsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getRandomInsult(): InsultDTO =
        httpClient.get("${BuildConfig.INSULTS_API_BASE_URL}") {
            url {
                parameters.append("lang", "ru")
                parameters.append("type", "json")
            }
        }.body()
}