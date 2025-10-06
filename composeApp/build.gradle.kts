import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    id("com.codingfeline.buildkonfig")
    id("com.google.devtools.ksp")
    id("dev.mokkery") version "2.7.0"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    js(IR) {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }

            }
        }
        binaries.executable()
    }

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//        binaries.executable()
//    }

    sourceSets {
        val desktopMain by getting
        val commonTest by getting
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            //KTOR ANDROID
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.cio)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //LOG TEST
            implementation(libs.slf4j.api)  // SLF4J API
            implementation(libs.logback.classic)  // Logback

            //KTOR
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)


            //DEPENDENCY INJECTION KOIN
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            //NAVIGATION
            implementation(libs.navigation.compose)

            //COIL
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.mp)

            //NAPIER LOGS
            implementation(libs.napier)

            //HAZE
            implementation(libs.haze)


            //SHIMMER
            implementation(libs.compose.shimmer)

            //MEDIA PLAYER


            // Exclude the logback dependencies to avoid duplicate META-INF files
            configurations.all {
                exclude(group = "ch.qos.logback", module = "logback-classic")
                exclude(group = "ch.qos.logback", module = "logback-core")
            }
        }
        commonTest{
            dependencies {
                implementation(kotlin("test")) // Para test unitarios en KMP
                implementation(libs.koinTest)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
            }
        }
        iosMain.dependencies {
            //KTOR
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.cio)

        }
        jsMain.dependencies {
            // KTOR JS
            implementation(libs.ktor.client.js)  // Ensure you're using the correct version
        }
    }
}

android {
    namespace = "com.tallerprogramacion.movieapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.tallerprogramacion.movieapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            exclude("META-INF/INDEX.LIST") // Exclude the conflicting INDEX.LIST
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.tallerprogramacion.movieapp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.tallerprogramacion.movieapp"
            packageVersion = "1.0.0"
        }
    }
}
val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

buildkonfig {
    packageName = "com.tallerprogramacion.movieapp"

    defaultConfigs {
        buildConfigField (
            FieldSpec.Type.STRING, "TMDB_API_KEY", (localProperties.getProperty("TMDB_API_KEY") ?: "missing api key")
        )
    }
}



dependencies {
    implementation(libs.androidx.tools.core)
    testImplementation("app.cash.turbine:turbine:0.4.0")
}
