package ru.heatalways.amazingasfuckapplication.feature.pidors.compose_impl.data.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ru.heatalways.amazingasfuckapplication.feature.pidors.api.domain.PidorAvatarCrop
import java.io.File
import java.util.UUID

internal class PidorsAvatarsStorage(
    private val applicationContext: Context,
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun save(file: File, avatarCrop: PidorAvatarCrop): String {
        val destinationFileName = UUID.randomUUID().toString()

        val destinationFile = File(applicationContext.filesDir, "$FOLDER_NAME/$destinationFileName")

        val parent = destinationFile.parentFile

        check(parent == null || parent.exists() || parent.mkdirs()) {
            "Couldn't create dir: $parent"
        }

        destinationFile.createNewFile()

        when (avatarCrop) {
            is PidorAvatarCrop.Exactly -> {
                val newBitmap = Bitmap.createBitmap(
                    BitmapFactory.decodeFile(file.path),
                    avatarCrop.left,
                    avatarCrop.top,
                    avatarCrop.width,
                    avatarCrop.height
                )

                destinationFile.outputStream().use { outputStream ->
                    newBitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        BITMAP_SAVING_QUALITY,
                        outputStream
                    )
                }
            }
            PidorAvatarCrop.Full -> {
                file.copyTo(destinationFile, overwrite = true)
            }
        }

        Log.d(TAG, "File saved: ${destinationFile.path}")

        return destinationFile.path
    }

    suspend fun delete(path: String) {
        File(path).delete()
    }

    companion object {
        private const val TAG = "PidorsAvatarsStorage"
        private const val FOLDER_NAME = "pidors_avatars"
        private const val BITMAP_SAVING_QUALITY = 100
    }
}