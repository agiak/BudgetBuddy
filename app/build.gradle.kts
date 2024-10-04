@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ksp)
}

android {
    namespace = ProjectVariables.BASE_NAMESPACE
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.mywallet"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    
    implementation(project(":common"))
    implementation(project(":core"))

    // Features
    implementation(project(":features:home:public"))
    implementation(project(":features:statics:public"))
    implementation(project(":features:quicklogin:public"))
    implementation(project(":features:register:public"))
    implementation(project(":features:rules:public"))
    implementation(project(":features:guide:public"))
    implementation(project(":features:accounts:public"))
    implementation(project(":features:account:public"))
    implementation(project(":features:accountAdd:public"))
    implementation(project(":features:transactionAdd:public"))
    implementation(project(":features:transactions:public"))
    implementation(project(":features:transaction:public"))
    implementation(project(":features:transactionsViaFile:public"))
    implementation(project(":features:profile:public"))
    implementation(project(":features:more:public"))

    // Kotlin
    implementation(libs.bundles.kotlin.main)

    // UI components
    implementation(libs.bundles.ui.components)

    // Lifecycle components
    implementation(libs.bundles.lifecycle.components)

    // Dagger - Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    // Navigation
    implementation(libs.bundles.navigation)

    // Network (Retrofit)
    implementation(libs.bundles.network)

    // Local database (Room)
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.hawk)

    // WorkManager
    implementation(libs.workManager)

    // OpenAI
    implementation(libs.bundles.chatGPT)

    // Image loading
    implementation(libs.glide)

    // Logging
    implementation(libs.timber)

    // CSV parsing library
    implementation(libs.bundles.csv.parsing)

    // Testing
    testImplementation(libs.bundles.testImplementationLibs)
    androidTestImplementation(libs.bundles.androidTestImplementationLibs)
}
