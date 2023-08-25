package ru.heatalways.amazingasfuckapplication

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.heatalways.amazingasfuckapplication.di.catsModule
import ru.heatalways.amazingasfuckapplication.di.dispatchersModule
import ru.heatalways.amazingasfuckapplication.di.factsModule
import ru.heatalways.amazingasfuckapplication.di.httpClientModule
import ru.heatalways.amazingasfuckapplication.di.insultsModule
import ru.heatalways.amazingasfuckapplication.di.navigationModule
import ru.heatalways.amazingasfuckapplication.di.translatorModule
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
                translatorModule,
                factsModule,
                insultsModule,
                catsModule,
            )
        }

        Coil.setImageLoader {
            ImageLoader.Builder(this)
                .components {
                    if (android.os.Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
        }
    }
}