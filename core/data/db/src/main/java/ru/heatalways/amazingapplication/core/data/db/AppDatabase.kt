package ru.heatalways.amazingapplication.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.heatalways.amazingapplication.core.data.db.daos.HateTopDAO
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity

@Database(entities = [HateTopEntity::class], version = AppDatabase.VERSION)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun hateTopDAO(): HateTopDAO

    companion object {
        const val VERSION = 1
        const val NAME = "APP_DB"
    }
}