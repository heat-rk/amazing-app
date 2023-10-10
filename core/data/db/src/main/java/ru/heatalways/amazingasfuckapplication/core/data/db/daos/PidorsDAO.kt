package ru.heatalways.amazingasfuckapplication.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity.Companion.COLUMN_AVATAR_PATH
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity.Companion.COLUMN_ID
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity.Companion.COLUMN_NAME
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity.Companion.COLUMN_TAP_COUNT
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity.Companion.TABLE_NAME

@Dao
interface PidorsDAO {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_TAP_COUNT DESC")
    suspend fun getAllSorted(): List<PidorEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=:id")
    suspend fun get(id: Long): PidorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pidor: PidorEntity)

    @Query("UPDATE $TABLE_NAME SET $COLUMN_TAP_COUNT=:tapCount WHERE $COLUMN_ID=:id")
    suspend fun update(id: Long, tapCount: Int): Int

    @Query("UPDATE $TABLE_NAME SET $COLUMN_NAME=:name, $COLUMN_AVATAR_PATH=:avatarPath WHERE $COLUMN_ID=:id")
    suspend fun update(id: Long, name: String, avatarPath: String): Int

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID in (:ids)")
    suspend fun delete(ids: List<Long>): Int
}