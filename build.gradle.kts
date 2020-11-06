buildscript {
    addScriptRepository()
    addScriptDependencies()
}

allprojects {
    addScriptRepository()

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
//        kotlinOptions.allWarningsAsErrors = true

        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
        kotlinOptions.freeCompilerArgs += "-Xallow-jvm-ir-dependencies"
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
