object AppDependencies {
    const val immutableCollections = "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.immutableCollections}"

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
        val allImplementations = arrayOf(systemUiController)
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val material = "androidx.compose.material3:material3"
        const val preview = "androidx.compose.ui:ui-tooling-preview"
        const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
        const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"

        const val debugPreview = "androidx.compose.ui:ui-tooling"

        val platformImplementations = arrayOf(bom)
        val allImplementations = arrayOf(material, preview, activity, viewModel, navigation, lifeCycleRuntime)
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
    }
}