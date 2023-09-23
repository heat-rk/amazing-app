package ru.heatalways.amazingasfuckapplication.feature.sharing.api

import ru.heatalways.amazingasfuckapplication.common.utils.StringResource

interface Sharing {
    suspend fun shareLink(link: String)
    suspend fun shareText(text: String)
    suspend fun shareText(text: StringResource)
}