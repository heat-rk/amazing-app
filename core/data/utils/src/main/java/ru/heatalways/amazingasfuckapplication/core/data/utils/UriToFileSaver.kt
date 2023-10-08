package ru.heatalways.amazingasfuckapplication.core.data.utils

import android.content.Context
import android.net.Uri
import java.io.File

class UriToFileSaver(
    private val applicationContext: Context
) {
    fun save(uri: Uri, destination: File) {
        applicationContext.contentResolver.openInputStream(uri)?.use { input ->
            destination.outputStream().use { output ->
                input.copyTo(output)
                output.flush()
            }
        }
    }
}