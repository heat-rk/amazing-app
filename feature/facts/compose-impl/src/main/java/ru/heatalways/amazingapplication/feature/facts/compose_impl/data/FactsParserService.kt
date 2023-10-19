package ru.heatalways.amazingapplication.feature.facts.compose_impl.data

import org.jsoup.Jsoup
import ru.heatalways.amazingapplication.feature.facts.compose_impl.BuildConfig
import java.io.IOException

class FactsParserService {
    suspend fun getRandomFact(): FactDTO {
        val doc = Jsoup.connect(BuildConfig.FACTS_WEB_SERVICE_BASE_URL).get()

        val text = doc
            .getElementById("fact")
            ?.firstElementChild()
            ?.text() ?: throw IOException()

        return FactDTO(fact = text)
    }
}