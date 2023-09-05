package ru.heatalways.amazingasfuckapplication.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.data.common.database.AppDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = AppDatabase::class.java,
            name = AppDatabase.NAME
        ).build()
    }
}