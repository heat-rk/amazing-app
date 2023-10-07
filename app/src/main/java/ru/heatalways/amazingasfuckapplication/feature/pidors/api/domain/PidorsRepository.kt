package ru.heatalways.amazingasfuckapplication.domain.pidors

import kotlinx.coroutines.flow.Flow
import java.io.File

interface PidorsRepository {
    suspend fun observeAllSorted(): Flow<List<Pidor>>

    suspend fun create(
        id: Long = -1,
        avatarFile: File,
        avatarCrop: PidorAvatarCrop,
        name: String,
    )

    suspend fun update(id: Long, tapCount: Int)

    suspend fun delete(id: Long)

    suspend fun delete(ids: List<Long>)
}