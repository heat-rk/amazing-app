import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.detekt(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("detekt", dependency)
    }
}

fun DependencyHandler.kapt(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.api(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("api", dependency)
    }
}

fun DependencyHandler.debugImplementation(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}

fun DependencyHandler.platformImplementation(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("implementation", platform(dependency))
    }
}

fun DependencyHandler.androidTestImplementation(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(dependencies: Array<String>) {
    dependencies.forEach { dependency ->
        add("testImplementation", dependency)
    }
}