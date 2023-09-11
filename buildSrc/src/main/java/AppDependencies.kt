object AppDependencies {
    const val immutableCollections = "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.immutableCollections}"
    const val htmlUnit = "net.sourceforge.htmlunit:htmlunit-android:${Versions.htmlUnit}"

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val engine = "io.ktor:ktor-client-cio:${Versions.ktor}"
        const val negotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"

        val allImplementations = arrayOf(core, engine, negotiation, serialization, logging)
    }

    object Koin {
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
        val allImplementations = arrayOf(compose)
    }

    object Accompanist {
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.systemUiController}"
        const val permissions = "com.google.accompanist:accompanist-permissions:${Versions.permission}"
        val allImplementations = arrayOf(systemUiController, permissions)
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val material = "androidx.compose.material3:material3"
        const val preview = "androidx.compose.ui:ui-tooling-preview"
        const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
        const val activityCore = "androidx.activity:activity:${Versions.composeActivity}"
        const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"

        const val debugPreview = "androidx.compose.ui:ui-tooling"

        val platformImplementations = arrayOf(bom)
        val allImplementations = arrayOf(material, preview, activityCore, activity, viewModel, navigation, lifeCycleRuntime)
        val debugImplementations = arrayOf(debugPreview)
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

        val allImplementations = arrayOf(core, android)
    }

    object Ktx {
        const val core = "androidx.core:core-ktx:${Versions.coreKtx}"
        val allImplementations = arrayOf(core)
    }

    object Mlkit {
        const val translate = "com.google.mlkit:translate:${Versions.mlkitTranslate}"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:${Versions.coil}"
        const val gif = "io.coil-kt:coil-gif:${Versions.coil}"
        val allImplementations = arrayOf(compose, gif)
    }

    object Camera {
        const val lifecycle = "androidx.camera:camera-lifecycle:${Versions.camera}"
        const val view = "androidx.camera:camera-view:${Versions.camera}"
        const val extensions = "androidx.camera:camera-extensions:${Versions.camera}"
        val allImplementations = arrayOf(view, lifecycle, extensions)
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        val allImplementations = arrayOf(runtime, ktx)
    }

    object Orbit {
        const val viewModel = "org.orbit-mvi:orbit-viewmodel:${Versions.orbit}"
        const val compose = "org.orbit-mvi:orbit-compose:${Versions.orbit}"
        val allImplementations = arrayOf(viewModel, compose)
    }

    object Versions {
        const val koin = "3.4.6"
        const val coreKtx = "1.10.1"
        const val navigation = "2.5.1"
        const val coroutines = "1.7.3"
        const val detekt = "1.23.1"
        const val composeBom = "2023.08.00"
        const val composeActivity = "1.7.2"
        const val composeViewModel = "2.6.1"
        const val composeNavigation = "2.7.0"
        const val immutableCollections = "0.3.5"
        const val lifecycle = "2.6.1"
        const val systemUiController = "0.30.1"
        const val ktor = "2.3.3"
        const val mlkitTranslate = "17.0.1"
        const val htmlUnit = "2.67.0"
        const val coil = "2.4.0"
        const val camera = "1.2.3"
        const val permission = "0.32.0"
        const val room = "2.5.2"
        const val orbit = "6.0.0"
    }
}