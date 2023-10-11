package ru.heatalways.amazingapplication.feature.sharing.android_impl

import android.content.Context
import android.content.Intent
import ru.heatalways.amazingapplication.common.utils.StringResource
import ru.heatalways.amazingapplication.common.utils.extract
import ru.heatalways.amazingapplication.feature.sharing.api.Sharing

class AndroidSharing(
    private val context: Context
) : Sharing {
    override suspend fun shareLink(link: String) {
        startChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, link)
            }
        )
    }

    override suspend fun shareText(text: String) {
        startChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
        )
    }

    override suspend fun shareText(text: StringResource) {
        text.extract(context)?.let { shareText(it) }
    }

    private fun startChooser(intent: Intent) {
        context.startActivity(
            Intent.createChooser(intent,  null).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
}