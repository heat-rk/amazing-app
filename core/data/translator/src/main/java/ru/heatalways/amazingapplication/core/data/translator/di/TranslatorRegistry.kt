package ru.heatalways.amazingapplication.core.data.translator.di

import com.google.mlkit.nl.translate.TranslateLanguage
import ru.heatalways.amazingapplication.core.data.translator.EnglishToRuTranslator
import ru.heatalways.amazingapplication.core.data.translator.Translator
import scout.definition.Registry

fun Registry.useTranslatorBeans() {
    singleton<EnglishToRuTranslator> {
        EnglishToRuTranslator(
            Translator(
                sourceLanguage = TranslateLanguage.ENGLISH,
                targetLanguage = TranslateLanguage.RUSSIAN
            )
        )
    }
}