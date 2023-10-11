package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.heatalways.amazingapplication.core.data.cache.InMemoryCacheContainer
import ru.heatalways.amazingapplication.core.data.db.daos.HateTopDAO
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopUnit
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopUnitAvatarCrop
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopRepository
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.data.storage.HateTopAvatarsStorage
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.mappers.toDomain
import java.io.File

internal class HateTopRepositoryImpl(
    private val hateTopDao: HateTopDAO,
    private val inMemoryCacheDataSource: InMemoryCacheContainer<List<HateTopUnit>>,
    private val dispatcher: CoroutineDispatcher,
    private val longRunningScope: CoroutineScope,
    private val avatarsStorage: HateTopAvatarsStorage,
) : HateTopRepository {

    override suspend fun observeAllSorted() = withContext(dispatcher) {
        inMemoryCacheDataSource.valueFlow(
            defaultDataProvider = { hateTopDao.getAllSorted().map { it.toDomain() } }
        )
    }

    override suspend fun create(
        id: Long,
        avatarFile: File,
        avatarCrop: HateTopUnitAvatarCrop,
        name: String
    ) {
        longRunningScope.launch(dispatcher) {
            val avatarFileName = avatarsStorage.save(avatarFile, avatarCrop)

            if (id == -1L) {
                hateTopDao.insert(
                    HateTopEntity(
                        name = name,
                        avatarPath = avatarFileName,
                        tapCount = 0
                    )
                )
            } else {
                hateTopDao.update(
                    id = id,
                    name = name,
                    avatarPath = avatarFileName,
                )
            }

            updateInMemoryCache()
        }.join()
    }

    override suspend fun update(id: Long, tapCount: Int) {
        longRunningScope.launch(dispatcher) {
            hateTopDao.update(id, tapCount)
            updateInMemoryCache()
        }.join()
    }

    override suspend fun delete(id: Long) {
        longRunningScope.launch(dispatcher) {
            inMemoryCacheDataSource.value
                ?.find { it.id == id }
                ?.let { avatarsStorage.delete(it.avatarPath) }

            hateTopDao.delete(id)

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

            hateTopDao.delete(ids)

            updateInMemoryCache()
        }.join()
    }

    private suspend fun updateInMemoryCache() {
        inMemoryCacheDataSource.value = hateTopDao.getAllSorted().map { it.toDomain() }
    }
}