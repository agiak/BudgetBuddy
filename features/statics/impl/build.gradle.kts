plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
    targetCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
}