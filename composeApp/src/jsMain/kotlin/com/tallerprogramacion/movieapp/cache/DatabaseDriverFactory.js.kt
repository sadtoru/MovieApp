package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import kotlinx.coroutines.await

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return initSqlDriver(MovieDatabase.Schema).await()
    }
}

// Global variable to store the pre-initialized driver for JS
private var preInitializedDriver: SqlDriver? = null

// Function to initialize the driver before Koin starts (called from main)
suspend fun initializeJsDriver(factory: DatabaseDriverFactory): SqlDriver {
    val driver = factory.createDriver()
    preInitializedDriver = driver
    return driver
}

actual fun createDriverSync(factory: DatabaseDriverFactory): SqlDriver {
    // Return the pre-initialized driver if available, otherwise throw an error
    return preInitializedDriver ?: error("Database driver not initialized. Call initializeJsDriver() before initKoin()")
}