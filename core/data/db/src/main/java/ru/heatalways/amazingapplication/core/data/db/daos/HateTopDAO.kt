package ru.heatalways.amazingapplication.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity.Companion.COLUMN_AVATAR_PATH
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity.Companion.COLUMN_ID
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity.Companion.COLUMN_NAME
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity.Companion.COLUMN_TAP_COUNT
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity.Companion.TABLE_NAME

@Dao
interface HateTopDAO {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_TAP_COUNT DESC")
    suspend fun getAllSorted(): List<HateTopEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=:id")
    suspend fun get(id: Long): HateTopEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HateTopEntity)

    @Query("UPDATE $TABLE_NAME SET $COLUMN_TAP_COUNT=:tapCount WHERE $COLUMN_ID=:id")
    suspend fun update(id: Long, tapCount: Int): Int

    @Query("UPDATE $TABLE_NAME SET $COLUMN_NAME=:name, $COLUMN_AVATAR_PATH=:avatarPath WHERE $COLUMN_ID=:id")
    suspend fun update(id: Long, name: String, avatarPath: String): Int

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID in (:ids)")
    suspend fun delete(ids: List<Long>): Int
}