package ru.heatalways.amazingasfuckapplication

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.heatalways.amazingasfuckapplication.core.coroutines.dispatchers.dispatchersModule
import ru.heatalways.amazingasfuckapplication.core.coroutines.scopes.di.coroutineScopesModule
import ru.heatalways.amazingasfuckapplication.core.data.db.di.databaseModule
import ru.heatalways.amazingasfuckapplication.core.data.http_client.di.httpClientModule
import ru.heatalways.amazingasfuckapplication.core.data.temp_files.di.tempFilesStorageModule
import ru.heatalways.amazingasfuckapplication.core.data.translator.di.translatorModule
import ru.heatalways.amazingasfuckapplication.core.data.utils.di.dataUtilsModule
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.di.composeNavigationModule
import ru.heatalways.amazingasfuckapplication.feature.cats.compose_impl.di.catsModule
import ru.heatalways.amazingasfuckapplication.feature.facts.compose_impl.di.factsModule
import ru.heatalways.amazingasfuckapplication.feature.insults.compose_impl.di.insultsModule
import ru.heatalways.amazingasfuckapplication.feature.menu.impl.di.menuModule
import ru.heatalways.amazingasfuckapplication.feature.mirror.compose_impl.di.mirrorModule
import ru.heatalways.amazingasfuckapplication.feature.pidors.compose_impl.di.pidorsModule
import ru.heatalways.amazingasfuckapplication.feature.remember.compose_impl.di.rememberModule
import ru.heatalways.amazingasfuckapplication.feature.sharing.android_impl.di.androidSharingModule

class AmazingAsFuckApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AmazingAsFuckApplication)

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
                pidorsModule,
                rememberModule,
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