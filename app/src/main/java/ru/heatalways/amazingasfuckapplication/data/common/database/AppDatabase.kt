package ru.heatalways.amazingasfuckapplication.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorsDatabaseSource

@Database(entities = [PidorDAO::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pidors(): PidorsDatabaseSource

    companion object {
        const val VERSION = 1
        const val NAME = "APP_DB"
    }
}