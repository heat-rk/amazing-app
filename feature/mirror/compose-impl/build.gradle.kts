plugins {
    id(AppPlugins.androidLibrary)
    id(AppPlugins.androidKotlin)
}

android {
    namespace = "ru.heatalways.amazingasfuckapplication.feature.mirror.compose_impl"

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
    implementation(project(":feature:mirror:api"))
    implementation(project(":common:utils"))
    implementation(project(":core:design"))
    implementation(project(":core:navigation:api"))
    implementation(project(":core:composables:heart"))

    // Dependencies
    implementation(AppDependencies.Koin.allImplementations)
    implementation(AppDependencies.Camera.allImplementations)
    implementation(AppDependencies.Accompanist.permissions)
    implementation(AppDependencies.Compose.material)
    implementation(AppDependencies.Compose.preview)
    implementation(AppDependencies.Orbit.allImplementations)

    debugImplementation(AppDependencies.Compose.debugImplementations)
}