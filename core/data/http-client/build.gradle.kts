plugins {
    id(AppPlugins.androidLibrary)
    id(AppPlugins.androidKotlin)
}

android {
    namespace = "ru.heatalways.amazingasfuckapplication.core.data.http_client"

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
    }
}

dependencies {
    // dependencies
    implementation(AppDependencies.Ktor.allImplementations)
    implementation(AppDependencies.Koin.allImplementations)
}