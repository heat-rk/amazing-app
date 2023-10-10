package ru.heatalways.amazingasfuckapplication.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.heatalways.amazingasfuckapplication.core.data.db.daos.PidorsDAO
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity

@Database(entities = [PidorEntity::class], version = AppDatabase.VERSION)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun pidors(): PidorsDAO

    companion object {
        const val VERSION = 1
        const val NAME = "APP_DB"
    }
}