package ru.heatalways.amazingapplication.feature.facts.compose_impl.data

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import java.io.IOException

class FactsParserService {
    private val webClient = WebClient(BrowserVersion.BEST_SUPPORTED).apply {
        options.isThrowExceptionOnScriptError = false
        options.isThrowExceptionOnFailingStatusCode = false
    }

    private var page: HtmlPage? = null

    suspend fun getRandomFact(): FactDTO {
        page = webClient.getPage(FACTS_URL)

        val text = page
            ?.getElementById("fact")
            ?.firstChild
            ?.asNormalizedText() ?: throw IOException()

        return FactDTO(fact = text)
    }

    private companion object {
        private const val FACTS_URL = "https://randstuff.ru/fact/"
    }
}