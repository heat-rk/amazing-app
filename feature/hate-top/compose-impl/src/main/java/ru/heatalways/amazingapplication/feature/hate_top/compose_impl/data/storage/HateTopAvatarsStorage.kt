package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopUnitAvatarCrop
import java.io.File
import java.util.UUID

internal class HateTopAvatarsStorage(
    private val applicationContext: Context,
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun save(file: File, avatarCrop: HateTopUnitAvatarCrop): String {
        val destinationFileName = UUID.randomUUID().toString()

        val destinationFile = File(applicationContext.filesDir, "$FOLDER_NAME/$destinationFileName")

        val parent = destinationFile.parentFile

        check(parent == null || parent.exists() || parent.mkdirs()) {
            "Couldn't create dir: $parent"
        }

        destinationFile.createNewFile()

        when (avatarCrop) {
            is HateTopUnitAvatarCrop.Exactly -> {
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
            HateTopUnitAvatarCrop.Full -> {
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
        private const val TAG = "HateTopAvatarsStorage"
        private const val FOLDER_NAME = "hate_top_avatars"
        private const val BITMAP_SAVING_QUALITY = 100
    }
}