// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(AppPlugins.androidApplication) version AppPlugins.Versions.androidApplication apply false
    id(AppPlugins.androidKotlin) version AppPlugins.Versions.androidKotlin apply false
    id(AppPlugins.detekt) version AppPlugins.Versions.detekt
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

tasks.register("copyGitHooks", Copy::class.java) {
    description = "Copies the git hooks from /git-hooks to the .git folder."
    group = "git hooks"
    from("$rootDir/scripts/pre-commit")
    into("$rootDir/.git/hooks/")
}

tasks.register("installGitHooks", Exec::class.java) {
    description = "Installs the pre-commit git hooks from /git-hooks."
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("Git hook installed successfully.")
    }
}

afterEvaluate {
    tasks.findByPath(":app:clean")?.dependsOn(":installGitHooks")
    tasks.findByPath(":app:assemble")?.dependsOn(":installGitHooks")
}