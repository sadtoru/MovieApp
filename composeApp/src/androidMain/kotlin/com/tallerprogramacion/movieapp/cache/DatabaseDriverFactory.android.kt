package com.tallerprogramacion.movieapp.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.tallerprogramacion.movieapp.cache.MovieDatabase
import kotlinx.coroutines.runBlocking

actual class DatabaseDriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MovieDatabase.Schema,
            context = context,
            name = "movie.db"
        )
    }
}

actual fun createDriverSync(factory: DatabaseDriverFactory): SqlDriver {
    return runBlocking { factory.createDriver() }
}

