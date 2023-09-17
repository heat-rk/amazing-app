package ru.heatalways.amazingasfuckapplication.data.common.temp_files

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class TempFilesStorage(
    private val applicationContext: Context,
    private val dispatcher: CoroutineDispatcher,
) {
    private val lock = Mutex()

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun new(): File = lock.withLock {
        withContext(dispatcher) {
            val destinationFileName = UUID.randomUUID().toString()
            val destinationFile = File(applicationContext.filesDir, "$FOLDER_NAME/$destinationFileName")
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
            val clearResult = File(applicationContext.filesDir, FOLDER_NAME).deleteRecursively()
            Log.d(TAG, "Temp files storage clear result = $clearResult")
        }
    }

    companion object {
        private const val TAG = "TempFilesStorage"
        private const val FOLDER_NAME = "temp"
    }
}