package ru.heatalways.amazingasfuckapplication.core.data.translator.di

import com.google.mlkit.nl.translate.TranslateLanguage
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.data.translator.Translator

val EnglishToRuTranslatorQualifier = qualifier("EnglishToAppTranslatorQualifier")

val translatorModule = module {
    single(EnglishToRuTranslatorQualifier) {
        Translator(
            sourceLanguage = TranslateLanguage.ENGLISH,
            targetLanguage = TranslateLanguage.RUSSIAN
        )
    }
}