package ru.heatalways.amazingasfuckapplication.domain.pidors

interface PidorsRepository {
    suspend fun getAllSorted(): List<Pidor>

    suspend fun create(
        avatarPath: String,
        name: String,
    )

    suspend fun update(id: Long, tapCount: Int)

    suspend fun delete(id: Long)
}