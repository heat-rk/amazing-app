package ru.heatalways.amazingasfuckapplication.data.common.utils

import android.content.Context
import android.net.Uri
import okio.use
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