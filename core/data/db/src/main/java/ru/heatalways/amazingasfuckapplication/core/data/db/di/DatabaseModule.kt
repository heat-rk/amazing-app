package ru.heatalways.amazingasfuckapplication.core.data.db.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.heatalways.amazingasfuckapplication.core.data.db.AppDatabase

private val internalDatabaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = AppDatabase::class.java,
            name = AppDatabase.NAME
        ).build()
    }
}

val databaseModule = module {
    includes(internalDatabaseModule)

    factory {
        get<AppDatabase>().pidors()
    }
}