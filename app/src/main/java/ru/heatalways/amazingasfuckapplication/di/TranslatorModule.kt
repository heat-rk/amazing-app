package ru.heatalways.amazingasfuckapplication.di

import com.google.mlkit.nl.translate.TranslateLanguage
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.translator.Translator

val EnglishToRuTranslatorQualifier = qualifier("EnglishToAppTranslatorQualifier")

val translatorModule = module {
    single(EnglishToRuTranslatorQualifier) {
        Translator(
            sourceLanguage = TranslateLanguage.ENGLISH,
            targetLanguage = TranslateLanguage.RUSSIAN
        )
    }
}