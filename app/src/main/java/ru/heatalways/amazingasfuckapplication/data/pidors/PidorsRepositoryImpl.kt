package ru.heatalways.amazingasfuckapplication.data.pidors

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.data.common.cache.InMemoryCacheContainer
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorsDatabaseSource
import ru.heatalways.amazingasfuckapplication.data.pidors.storage.PidorsAvatarsStorage
import ru.heatalways.amazingasfuckapplication.domain.pidors.Pidor
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toDomain

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

    override suspend fun create(avatarUri: String, name: String) {
        longRunningScope.launch(dispatcher) {
            val avatarFileName = avatarsStorage.save(Uri.parse(avatarUri))

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
            dbDataSource.delete(id)
            updateInMemoryCache()
        }.join()
    }

    override suspend fun delete(ids: List<Long>) {
        longRunningScope.launch(dispatcher) {
            dbDataSource.delete(ids)
            updateInMemoryCache()
        }.join()
    }

    private suspend fun updateInMemoryCache() {
        inMemoryCacheDataSource.value = dbDataSource.getAllSorted().map { it.toDomain() }
    }
}