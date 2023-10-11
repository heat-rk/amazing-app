package ru.heatalways.amazingapplication.common.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull


fun Uri.obtainFileName(context: Context): String? {
    var result: String? = null

    if (scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)

        cursor?.use {
            cursor.moveToFirst()

            val displayNameColumnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

            if (displayNameColumnIndex >= 0) {
                result = cursor.getStringOrNull(displayNameColumnIndex)
            }
        }
    }

    if (result == null) {
        result = path

        val cut = path?.lastIndexOf('/')

        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }

    return result
}