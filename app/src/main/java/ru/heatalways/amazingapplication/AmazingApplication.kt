package ru.heatalways.amazingapplication

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.dispatchersModule
import ru.heatalways.amazingapplication.core.coroutines.scopes.di.coroutineScopesModule
import ru.heatalways.amazingapplication.core.data.db.di.databaseModule
import ru.heatalways.amazingapplication.core.data.http_client.di.httpClientModule
import ru.heatalways.amazingapplication.core.data.temp_files.di.tempFilesStorageModule
import ru.heatalways.amazingapplication.core.data.translator.di.translatorModule
import ru.heatalways.amazingapplication.core.data.utils.di.dataUtilsModule
import ru.heatalways.amazingapplication.core.navigation.compose_impl.di.composeNavigationModule
import ru.heatalways.amazingapplication.feature.cats.compose_impl.di.catsModule
import ru.heatalways.amazingapplication.feature.facts.compose_impl.di.factsModule
import ru.heatalways.amazingapplication.feature.insults.compose_impl.di.insultsModule
import ru.heatalways.amazingapplication.feature.menu.impl.di.menuModule
import ru.heatalways.amazingapplication.feature.mirror.compose_impl.di.mirrorModule
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di.hateTopModule
import ru.heatalways.amazingapplication.feature.sharing.android_impl.di.androidSharingModule

class AmazingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AmazingApplication)

            modules(
                androidSharingModule,
                coroutineScopesModule,
                databaseModule,
                tempFilesStorageModule,
                dataUtilsModule,
                composeNavigationModule,
                httpClientModule,
                dispatchersModule,
                translatorModule,
                menuModule,
                factsModule,
                insultsModule,
                catsModule,
                hateTopModule,
                mirrorModule,
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