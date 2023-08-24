package ru.heatalways.amazingasfuckapplication.data.translator

import androidx.core.os.LocaleListCompat
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Translator(
    private val sourceLanguage: String,
    private val targetLanguage: String = getAppLanguage(),
) {
    private val translatorOptions = TranslatorOptions.Builder()
        .setSourceLanguage(sourceLanguage)
        .setTargetLanguage(targetLanguage)
        .build()

    private val translator = Translation.getClient(translatorOptions)

    private val downloadConditions = DownloadConditions.Builder().build()

    suspend fun translate(text: String): String {
        ensureDownloaded()

        return suspendCoroutine { continuation ->
            translator.translate(text)
                .addOnSuccessListener { translated ->
                    continuation.resume(translated)
                }
                .addOnFailureListener { throwable ->
                    continuation.resumeWithException(throwable)
                }
        }
    }

    private suspend fun ensureDownloaded() = suspendCoroutine { continuation ->
        translator.downloadModelIfNeeded(downloadConditions)
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener { throwable ->
                continuation.resumeWithException(throwable)
            }
    }

    companion object {
        private fun getAppLanguage() = LocaleListCompat.getDefault()[0]?.language
            ?: getDefaultLanguage()

        private fun getDefaultLanguage() = "ru"
    }
}