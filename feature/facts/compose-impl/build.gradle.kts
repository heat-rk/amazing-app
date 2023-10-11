plugins {
    id(AppPlugins.androidLibrary)
    id(AppPlugins.androidKotlin)
    id(AppPlugins.parcelize)

    kotlin(AppPlugins.serialization) version AppPlugins.Versions.serialization
}

android {
    namespace = "ru.heatalways.amazingapplication.feature.facts.compose_impl"

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
    implementation(project(":feature:facts:api"))
    implementation(project(":feature:sharing:api"))
    implementation(project(":common:utils"))
    implementation(project(":core:design"))
    implementation(project(":core:composables:pager"))
    implementation(project(":core:coroutines:dispatchers"))
    implementation(project(":core:data:translator"))
    implementation(project(":core:navigation:api"))

    // Dependencies
    implementation(AppDependencies.Koin.allImplementations)
    implementation(AppDependencies.Ktor.allImplementations)
    implementation(AppDependencies.Coil.allImplementations)
    implementation(AppDependencies.Compose.material)
    implementation(AppDependencies.htmlUnit)

    debugImplementation(AppDependencies.Compose.debugImplementations)
}