package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import com.tallerprogramacion.movieapp.cache.MovieDatabase
import kotlinx.coroutines.await

actual class DatabaseDriverFactory {
    private var driver: SqlDriver? = null
    
    actual fun createDriver(): SqlDriver {
        if (driver != null) {
            return driver!!
        }

        driver = kotlinx.coroutines.runBlocking {
            initSqlDriver(MovieDatabase.Schema).await()
        }
        return driver!!
    }
}