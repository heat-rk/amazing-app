package ru.heatalways.amazingasfuckapplication.data.pidors.storage

import android.content.Context
import android.util.Log
import java.io.File
import java.util.UUID

class PidorsAvatarsStorage(
    private val applicationContext: Context,
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun save(file: File): String {
        val destinationFileName = UUID.randomUUID().toString()

        val destinationFile = File(applicationContext.filesDir, "$FOLDER_NAME/$destinationFileName")

        val parent = destinationFile.parentFile

        check(parent == null || parent.exists() || parent.mkdirs()) {
            "Couldn't create dir: $parent"
        }

        destinationFile.createNewFile()

        file.copyTo(destinationFile, overwrite = true)

        Log.d(TAG, "File saved: ${destinationFile.path}")

        return destinationFile.path
    }

    suspend fun delete(path: String) {
        File(path).delete()
    }

    companion object {
        private const val TAG = "PidorsAvatarsStorage"
        private const val FOLDER_NAME = "pidors_avatars"
    }
}