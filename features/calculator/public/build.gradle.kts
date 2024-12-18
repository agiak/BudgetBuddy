plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ksp)
}

android {
    namespace = "${ProjectVariables.BASE_FEATURE_NAMESPACE}.calculator"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    // Implement 2 shared-common modules
    implementation(project(":common"))
    implementation(project(":core"))
    implementation(project(":features:calculator:impl"))

    // UI components
    implementation(libs.bundles.ui.components)

    // Lifecycle components
    implementation(libs.bundles.lifecycle.components)

    // Navigation
    implementation(libs.bundles.navigation)

    // Dagger - Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    // Logging
    implementation(libs.timber)

    // Testing
    testImplementation(libs.bundles.testImplementationLibs)
    androidTestImplementation(libs.bundles.androidTestImplementationLibs)
}