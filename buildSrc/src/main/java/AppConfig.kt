object AppConfig {
    const val applicationId = "ru.heatalways.amazingapplication"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val jvmTarget = "17"
    const val composeKotlinCompilerExtensionVersion = "1.4.8"

    val buildConfigFields = arrayOf(
        BuildConfigField(
            type = "String",
            name = "FACTS_API_BASE_URL",
            debugValue = "https://api.api-ninjas.com/v1",
            releaseValue = "https://api.api-ninjas.com/v1"
        ),
        BuildConfigField(
            type = "String",
            name = "INSULTS_API_BASE_URL",
            debugValue = "https://evilinsult.com/generate_insult.php",
            releaseValue = "https://evilinsult.com/generate_insult.php"
        ),
        BuildConfigField(
            type = "String",
            name = "CATS_API_BASE_URL",
            debugValue = "https://cataas.com",
            releaseValue = "https://cataas.com"
        ),
    )

    object Sdk {
        const val compile = 34
        const val min = 23
        const val target = 34
    }

    object Version {
        const val code = 1
        const val name = "1.0"
    }
}