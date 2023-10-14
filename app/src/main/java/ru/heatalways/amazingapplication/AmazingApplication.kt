package ru.heatalways.amazingapplication

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

class AmazingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        _instance = this

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

    companion object {
        private var _instance: AmazingApplication? = null
        val instance get() = requireNotNull(_instance)
    }
}