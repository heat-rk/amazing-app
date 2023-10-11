package ru.heatalways.amazingapplication.feature.hate_top.api.domain

import kotlinx.coroutines.flow.Flow
import java.io.File

interface HateTopRepository {
    suspend fun observeAllSorted(): Flow<List<HateTopUnit>>

    suspend fun create(
        id: Long = -1,
        avatarFile: File,
        avatarCrop: HateTopUnitAvatarCrop,
        name: String,
    )

    suspend fun update(id: Long, tapCount: Int)

    suspend fun delete(id: Long)

    suspend fun delete(ids: List<Long>)
}