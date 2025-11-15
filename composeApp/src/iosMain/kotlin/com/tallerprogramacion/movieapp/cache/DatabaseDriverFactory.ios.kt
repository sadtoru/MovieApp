package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.tallerprogramacion.movieapp.cache.MovieDatabase
import kotlinx.coroutines.runBlocking

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = MovieDatabase.Schema,
            name = "movie.db"
        )
    }
}

actual fun createDriverSync(factory: DatabaseDriverFactory): SqlDriver {
    return runBlocking { factory.createDriver() }
}


