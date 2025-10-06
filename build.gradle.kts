plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("com.codingfeline.buildkonfig") version "0.13.3" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.28"
}

buildscript {
    repositories {
        mavenCentral()

    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.buildkonfig.gradle.plugin)

    }
}