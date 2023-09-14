package ru.heatalways.amazingasfuckapplication.data.common.temp_files

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class TempFilesStorage(
    private val applicationContext: Context,
    private val dispatcher: CoroutineDispatcher,
) {
    private var isCleared = false

    private val lock = Mutex()

    private val folderName = "$FOLDER_NAME_BASE-${UUID.randomUUID()}"

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun new(): File = lock.withLock {
        withContext(dispatcher) {
            if (isCleared) {
                throw IllegalStateException("New files creating prohibited because TempFilesStorage is already cleared")
            }

            val destinationFileName = UUID.randomUUID().toString()
            val destinationFile = File(applicationContext.filesDir, "$folderName/$destinationFileName")
            val parent = destinationFile.parentFile

            check(parent == null || parent.exists() || parent.mkdirs()) {
                "Couldn't create dir: $parent"
            }

            destinationFile.createNewFile()

            Log.d(TAG, "File saved: ${destinationFile.path}")

            destinationFile
        }
    }

    suspend fun clear() = lock.withLock {
        withContext(dispatcher) {
            File(folderName).deleteRecursively()
            isCleared = true
        }
    }

    companion object {
        private const val TAG = "TempFilesStorage"
        private const val FOLDER_NAME_BASE = "temp"
    }
}