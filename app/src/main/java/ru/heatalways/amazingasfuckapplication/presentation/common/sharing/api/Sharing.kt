package ru.heatalways.amazingasfuckapplication.presentation.common.sharing.api

import ru.heatalways.amazingasfuckapplication.utils.StringResource

interface Sharing {
    suspend fun shareLink(link: String)
    suspend fun shareText(text: String)
    suspend fun shareText(text: StringResource)
}