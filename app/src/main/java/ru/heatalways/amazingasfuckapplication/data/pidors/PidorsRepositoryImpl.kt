package ru.heatalways.amazingasfuckapplication.data.pidors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.heatalways.amazingasfuckapplication.data.common.cache.InMemoryCacheContainer
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorsDatabaseSource
import ru.heatalways.amazingasfuckapplication.domain.pidors.Pidor
import ru.heatalways.amazingasfuckapplication.domain.pidors.PidorsRepository
import ru.heatalways.amazingasfuckapplication.mappers.toDAO
import ru.heatalways.amazingasfuckapplication.mappers.toDomain

class PidorsRepositoryImpl(
    private val dbDataSource: PidorsDatabaseSource,
    private val inMemoryCacheDataSource: InMemoryCacheContainer<List<Pidor>>,
    private val dispatcher: CoroutineDispatcher,
    private val longRunningScope: CoroutineScope,
) : PidorsRepository {

    override suspend fun getAllSorted(): List<Pidor> = withContext(dispatcher) {
        inMemoryCacheDataSource.getValueOrSave(dbDataSource.getAllSorted().map { it.toDomain() })
    }

    override suspend fun create(avatarPath: String, name: String) {
        longRunningScope.launch(dispatcher) {
            dbDataSource.insert(
                PidorDAO(
                    name = name,
                    avatarPath = avatarPath,
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

    private suspend fun updateInMemoryCache() {
        inMemoryCacheDataSource.value = dbDataSource.getAllSorted().map { it.toDomain() }
    }
}