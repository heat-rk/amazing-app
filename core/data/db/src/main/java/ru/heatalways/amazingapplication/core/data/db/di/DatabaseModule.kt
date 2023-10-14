package ru.heatalways.amazingapplication.core.data.db.di

import androidx.room.Room
import ru.heatalways.amazingapplication.core.data.db.AppDatabase
import ru.heatalways.amazingapplication.core.data.db.daos.HateTopDAO
import scout.definition.Registry

fun Registry.useDatabaseBeans() {
    singleton<AppDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = AppDatabase.NAME
        ).build()
    }

    reusable<HateTopDAO> { get<AppDatabase>().hateTopDAO() }
}