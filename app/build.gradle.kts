plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.pluu.composesample"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            proguardFiles(file("proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerVersion = Dep.Kotlin.version
        kotlinCompilerExtensionVersion = Dep.AndroidX.Compose.version
    }
}

dependencies {
    implementation(Dep.Kotlin.stdlibJvm)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.appcompat)
    implementation(Dep.AndroidX.lifecycle.runtime)

    implementation(Dep.AndroidX.UI.material)
    implementation(Dep.AndroidX.UI.preference)

    // Compose
    implementation(Dep.AndroidX.Compose.runtime)
    implementation(Dep.AndroidX.Compose.foundation)
    implementation(Dep.AndroidX.Compose.ui)
    implementation(Dep.AndroidX.Compose.layout)
    implementation(Dep.AndroidX.Compose.text)
    implementation(Dep.AndroidX.Compose.material)
    implementation(Dep.AndroidX.Compose.tooling)


    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)
}