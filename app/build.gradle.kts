plugins {
    id(AppPlugins.androidApplication)
    id(AppPlugins.androidKotlin)
    id(AppPlugins.kotlinKapt)
    id(AppPlugins.detekt) version AppPlugins.Versions.detekt
    id(AppPlugins.parcelize)

    kotlin(AppPlugins.serialization) version AppPlugins.Versions.serialization
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

    detekt {
        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = true
        config = files("${rootProject.rootDir}/detekt-config.yml")
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = AppConfig.jvmTarget
    }

    tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
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
    implementation(project(":core:composables:heart"))
    implementation(project(":core:composables:image-cropper"))
    implementation(project(":core:composables:pager"))
    implementation(project(":core:coroutines:dispatchers"))
    implementation(project(":core:coroutines:scopes"))
    implementation(project(":core:data:utils"))
    implementation(project(":core:data:translator"))
    implementation(project(":core:data:db"))
    implementation(project(":core:data:cache"))
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

    // dependencies
    platformImplementation(AppDependencies.Compose.platformImplementations)

    implementation(AppDependencies.immutableCollections)
    implementation(AppDependencies.Ktx.allImplementations)
    implementation(AppDependencies.Compose.allImplementations)
    implementation(AppDependencies.Coroutines.allImplementations)
    implementation(AppDependencies.Koin.allImplementations)
    implementation(AppDependencies.Accompanist.allImplementations)
    implementation(AppDependencies.Ktor.allImplementations)
    implementation(AppDependencies.Mlkit.translate)
    implementation(AppDependencies.htmlUnit)
    implementation(AppDependencies.Coil.allImplementations)
    implementation(AppDependencies.Camera.allImplementations)
    implementation(AppDependencies.Room.allImplementations)
    implementation(AppDependencies.Orbit.allImplementations)

    kapt(AppDependencies.Room.compiler)

    debugImplementation(AppDependencies.Compose.debugImplementations)
}