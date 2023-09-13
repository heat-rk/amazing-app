package ru.heatalways.amazingasfuckapplication.data.pidors.storage

import android.content.Context
import android.net.Uri
import android.util.Log
import okio.use
import ru.heatalways.amazingasfuckapplication.utils.obtainFileName
import java.io.File
import java.util.UUID

class PidorsAvatarsStorage(
    private val applicationContext: Context,
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun save(uri: Uri): String {
        val destinationFileName = buildString {
            append(UUID.randomUUID())
            append('-')
            append(uri.obtainFileName(applicationContext))
        }

        val destinationFile = File(applicationContext.filesDir, "$FOLDER_NAME/$destinationFileName")

        val parent = destinationFile.parentFile

        check(parent == null || parent.exists() || parent.mkdirs()) {
            "Couldn't create dir: $parent"
        }

        destinationFile.createNewFile()

        applicationContext.contentResolver.openInputStream(uri)?.use { input ->
            destinationFile.outputStream().use { output ->
                while (input.available() > 0) {
                    output.write(input.read())
                }

                output.flush()
            }
        }

        Log.d(TAG, "File saved: ${destinationFile.path}")

        return destinationFile.path
    }

    companion object {
        private const val TAG = "PidorsAvatarsStorage"
        private const val FOLDER_NAME = "pidors_avatars"
    }
}