plugins {
    id(AppPlugins.androidLibrary)
    id(AppPlugins.androidKotlin)
    id(AppPlugins.kotlinKapt)
}

android {
    namespace = "ru.heatalways.amazingapplication.feature.hate_top.compose_impl"

    compileSdk = AppConfig.Sdk.compile

    defaultConfig {
        minSdk = AppConfig.Sdk.min
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")

            AppConfig.buildConfigFields.forEach { field ->
                buildConfigField(field.type, field.name, "\"${field.releaseValue}\"")
            }
        }

        debug {
            AppConfig.buildConfigFields.forEach { field ->
                buildConfigField(field.type, field.name, "\"${field.debugValue}\"")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.composeKotlinCompilerExtensionVersion
    }
}

dependencies {
    // Modules
    implementation(project(":feature:hate-top:api"))
    implementation(project(":common:utils"))
    implementation(project(":core:design"))
    implementation(project(":core:coroutines:dispatchers"))
    implementation(project(":core:coroutines:scopes"))
    implementation(project(":core:navigation:api"))
    implementation(project(":core:composables:image-cropper"))
    implementation(project(":core:data:utils"))
    implementation(project(":core:data:db"))
    implementation(project(":core:data:cache"))
    implementation(project(":core:data:temp-files"))
    implementation(project(":core:data:http-client"))

    // Dependencies
    implementation(AppDependencies.Scout.allImplementations)
    implementation(AppDependencies.Coil.allImplementations)
    implementation(AppDependencies.Room.allImplementations)
    implementation(AppDependencies.Orbit.allImplementations)
    implementation(AppDependencies.Ktor.allImplementations)
    implementation(AppDependencies.Coroutines.allImplementations)
    implementation(AppDependencies.immutableCollections)
    implementation(AppDependencies.Compose.lifeCycleRuntime)
    implementation(AppDependencies.Compose.material)
    implementation(AppDependencies.Compose.preview)
    implementation(AppDependencies.Compose.activity)
    implementation(AppDependencies.Compose.viewModel)

    kapt(AppDependencies.Room.compiler)

    debugImplementation(AppDependencies.Compose.debugImplementations)
}