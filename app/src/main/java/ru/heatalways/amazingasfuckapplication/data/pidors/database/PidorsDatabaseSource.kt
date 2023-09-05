package ru.heatalways.amazingasfuckapplication.data.pidors.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO.Companion.TABLE_NAME
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO.Companion.COLUMN_TAP_COUNT
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO.Companion.COLUMN_ID

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

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun delete(id: Long): Int
}