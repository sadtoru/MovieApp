package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqlite.JvmSqliteDriver
import com.tallerprogramacion.movieapp.cache.MovieDatabase
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val databasePath = File(System.getProperty("java.io.tmpdir"), "movie.db")
        return JvmSqliteDriver(
            schema = MovieDatabase.Schema,
            path = databasePath.absolutePath
        )
    }
}

