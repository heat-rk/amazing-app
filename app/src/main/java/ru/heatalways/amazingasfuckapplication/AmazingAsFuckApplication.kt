package ru.heatalways.amazingasfuckapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.heatalways.amazingasfuckapplication.di.dispatchersModule
import ru.heatalways.amazingasfuckapplication.di.factsModule
import ru.heatalways.amazingasfuckapplication.di.httpClientModule
import ru.heatalways.amazingasfuckapplication.di.navigationModule
import ru.heatalways.amazingasfuckapplication.di.viewModelsModule

class AmazingAsFuckApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AmazingAsFuckApplication)

            modules(
                viewModelsModule,
                navigationModule,
                httpClientModule,
                dispatchersModule,
                factsModule,
            )
        }
    }
}