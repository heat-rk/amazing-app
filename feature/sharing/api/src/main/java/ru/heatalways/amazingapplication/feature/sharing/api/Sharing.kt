package ru.heatalways.amazingapplication.feature.sharing.api

import ru.heatalways.amazingapplication.common.utils.StringResource

interface Sharing {
    suspend fun shareLink(link: String)
    suspend fun shareText(text: String)
    suspend fun shareText(text: StringResource)
}