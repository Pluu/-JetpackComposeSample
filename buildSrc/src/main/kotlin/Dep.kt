@file:Suppress("ClassName")

object Dep {
    object GradlePlugin {
        const val androidStudioVersion = "4.2.0-alpha16"
        const val android = "com.android.tools.build:gradle:$androidStudioVersion"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    }

    object AndroidX {
        const val annotation = "androidx.annotation:annotation:1.2.0-alpha01"

        object activity {
            private const val activityVersion = "1.2.0-beta01"
            const val activity = "androidx.activity:activity:$activityVersion"
            const val ktx = "androidx.activity:activity-ktx:$activityVersion"
        }

        const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha02"
        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha05"

        object lifecycle {
            private const val version = "2.3.0-beta01"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${version}"
        }

        object UI {
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
            const val material = "com.google.android.material:material:1.3.0-alpha03"
            const val preference = "androidx.preference:preference:1.1.1"
        }

        object Compose {
            const val version = "1.0.0-alpha07"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val ui = "androidx.compose.ui:ui:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"
            const val text = "androidx.compose.foundation:foundation-text:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val materialAdapter = "com.google.android.material:compose-theme-adapter:${version}"
            const val tooling = "androidx.ui:ui-tooling:${version}"
            const val livedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val animation = "androidx.compose.animation:animation:$version"
        }
    }

    object Kotlin {
        const val version = "1.4.10"
        const val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13"
        const val androidJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}
