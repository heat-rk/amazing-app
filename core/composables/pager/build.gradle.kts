plugins {
    id(AppPlugins.androidLibrary)
    id(AppPlugins.androidKotlin)
}

android {
    namespace = "ru.heatalways.amazingapplication.core.composables.pager"

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
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.composeKotlinCompilerExtensionVersion
    }
}

dependencies {
    // Modules
    implementation(project(":core:design"))
    implementation(project(":core:navigation:api"))
    implementation(project(":common:utils"))

    // Dependencies
    implementation(AppDependencies.Compose.allImplementations)
    implementation(AppDependencies.immutableCollections)

    api(AppDependencies.Orbit.allImplementations)

    debugImplementation(AppDependencies.Compose.debugImplementations)
}