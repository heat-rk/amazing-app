plugins {
    id(AppPlugins.androidApplication)
    id(AppPlugins.androidKotlin)
    id(AppPlugins.kotlinKapt)
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.Sdk.compile

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.Sdk.min
        targetSdk = AppConfig.Sdk.target
        versionCode = AppConfig.Version.code
        versionName = AppConfig.Version.name

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

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
    // modules
    implementation(project(":common:utils"))
    implementation(project(":core:design"))
    implementation(project(":core:navigation:api"))
    implementation(project(":core:navigation:compose-impl"))
    implementation(project(":core:coroutines:dispatchers"))
    implementation(project(":core:coroutines:scopes"))
    implementation(project(":core:data:utils"))
    implementation(project(":core:data:translator"))
    implementation(project(":core:data:db"))
    implementation(project(":core:data:temp-files"))
    implementation(project(":core:data:http-client"))
    implementation(project(":feature:sharing:api"))
    implementation(project(":feature:sharing:android-impl"))
    implementation(project(":feature:cats:api"))
    implementation(project(":feature:cats:compose-impl"))
    implementation(project(":feature:facts:api"))
    implementation(project(":feature:facts:compose-impl"))
    implementation(project(":feature:insults:api"))
    implementation(project(":feature:insults:compose-impl"))
    implementation(project(":feature:mirror:api"))
    implementation(project(":feature:mirror:compose-impl"))
    implementation(project(":feature:hate-top:api"))
    implementation(project(":feature:hate-top:compose-impl"))

    // dependencies
    platformImplementation(AppDependencies.Compose.platformImplementations)

    implementation(AppDependencies.immutableCollections)
    implementation(AppDependencies.Ktx.allImplementations)

    implementation(AppDependencies.Coroutines.allImplementations)
    implementation(AppDependencies.Scout.allImplementations)
    implementation(AppDependencies.Accompanist.allImplementations)
    implementation(AppDependencies.Orbit.allImplementations)
    implementation(AppDependencies.Coil.allImplementations)
    implementation(AppDependencies.Compose.material)
    implementation(AppDependencies.Compose.lifeCycleRuntime)
    implementation(AppDependencies.Compose.activity)
    implementation(AppDependencies.Compose.navigation)
    implementation(AppDependencies.Compose.preview)

    debugImplementation(AppDependencies.Compose.debugImplementations)
}