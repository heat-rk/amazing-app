package ru.heatalways.amazingasfuckapplication.domain.pidors

import kotlinx.coroutines.flow.Flow

interface PidorsRepository {
    suspend fun observeAllSorted(): Flow<List<Pidor>>

    suspend fun create(
        avatarUri: String,
        name: String,
    )

    suspend fun update(id: Long, tapCount: Int)

    suspend fun delete(id: Long)

    suspend fun delete(ids: List<Long>)
}