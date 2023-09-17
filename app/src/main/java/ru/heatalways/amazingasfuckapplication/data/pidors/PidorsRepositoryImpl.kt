package ru.heatalways.amazingasfuckapplication.data.pidors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.data.common.cache.InMemoryCacheContainer
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorsDatabaseSource
import ru.heatalways.amazingasfuckapplication.data.pidors.storage.PidorsAvatarsStorage
import ru.heatalways.amazingasfuckapplication.domain.pidors.Pidor
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorAvatarCrop
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toDomain
import java.io.File

class PidorsRepositoryImpl(
    private val dbDataSource: PidorsDatabaseSource,
    private val inMemoryCacheDataSource: InMemoryCacheContainer<List<Pidor>>,
    private val dispatcher: CoroutineDispatcher,
    private val longRunningScope: CoroutineScope,
    private val avatarsStorage: PidorsAvatarsStorage,
) : PidorsRepository {

    override suspend fun observeAllSorted() = withContext(dispatcher) {
        inMemoryCacheDataSource.valueFlow(
            defaultDataProvider = { dbDataSource.getAllSorted().map { it.toDomain() } }
        )
    }

    override suspend fun create(
        avatarFile: File,
        avatarCrop: PidorAvatarCrop,
        name: String
    ) {
        longRunningScope.launch(dispatcher) {
            val avatarFileName = avatarsStorage.save(avatarFile, avatarCrop)

            dbDataSource.insert(
                PidorDAO(
                    name = name,
                    avatarPath = avatarFileName,
                    tapCount = 0
                )
            )

            updateInMemoryCache()
        }.join()
    }

    override suspend fun update(id: Long, tapCount: Int) {
        longRunningScope.launch(dispatcher) {
            dbDataSource.update(id, tapCount)
            updateInMemoryCache()
        }.join()
    }

    override suspend fun delete(id: Long) {
        longRunningScope.launch(dispatcher) {
            inMemoryCacheDataSource.value
                ?.find { it.id == id }
                ?.let { avatarsStorage.delete(it.avatarPath) }

            dbDataSource.delete(id)

            updateInMemoryCache()
        }.join()
    }

    override suspend fun delete(ids: List<Long>) {
        longRunningScope.launch(dispatcher) {
            inMemoryCacheDataSource.value
                ?.forEach {
                    if (ids.contains(it.id)) {
                        avatarsStorage.delete(it.avatarPath)
                    }
                }

            dbDataSource.delete(ids)

            updateInMemoryCache()
        }.join()
    }

    private suspend fun updateInMemoryCache() {
        inMemoryCacheDataSource.value = dbDataSource.getAllSorted().map { it.toDomain() }
    }
}