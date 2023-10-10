pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AmazingAsFuckApplication"

include(":app")
include(":core:design")
include(":common:utils")
include(":core:navigation:api")
include(":core:navigation:compose-impl")
include(":core:composables:heart")
include(":core:composables:image-cropper")
include(":core:composables:pager")
include(":core:coroutines:dispatchers")
include(":core:data:utils")
include(":core:data:translator")
include(":feature:sharing:api")
include(":feature:sharing:android-impl")
