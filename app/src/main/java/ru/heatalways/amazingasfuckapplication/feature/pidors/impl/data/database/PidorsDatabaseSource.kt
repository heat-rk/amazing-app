package ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.database.PidorDAO.Companion.TABLE_NAME
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.database.PidorDAO.Companion.COLUMN_TAP_COUNT
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.database.PidorDAO.Companion.COLUMN_AVATAR_PATH
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.database.PidorDAO.Companion.COLUMN_NAME
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.data.database.PidorDAO.Companion.COLUMN_ID

@Dao
interface PidorsDatabaseSource {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_TAP_COUNT DESC")
    suspend fun getAllSorted(): List<PidorDAO>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=:id")
    suspend fun get(id: Long): PidorDAO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pidor: PidorDAO)

    @Query("UPDATE $TABLE_NAME SET $COLUMN_TAP_COUNT=:tapCount WHERE $COLUMN_ID=:id")
    suspend fun update(id: Long, tapCount: Int): Int

    @Query("UPDATE $TABLE_NAME SET $COLUMN_NAME=:name, $COLUMN_AVATAR_PATH=:avatarPath WHERE $COLUMN_ID=:id")
    suspend fun update(id: Long, name: String, avatarPath: String): Int

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID in (:ids)")
    suspend fun delete(ids: List<Long>): Int
}