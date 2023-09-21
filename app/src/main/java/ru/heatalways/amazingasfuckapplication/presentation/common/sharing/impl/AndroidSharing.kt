package ru.heatalways.amazingasfuckapplication.presentation.common.sharing.impl

import android.content.Context
import android.content.Intent
import ru.heatalways.amazingasfuckapplication.presentation.common.sharing.api.Sharing
import ru.heatalways.amazingasfuckapplication.utils.StringResource
import ru.heatalways.amazingasfuckapplication.utils.extract

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