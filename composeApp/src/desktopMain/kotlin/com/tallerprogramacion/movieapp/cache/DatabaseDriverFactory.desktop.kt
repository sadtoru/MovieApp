package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.runBlocking

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:movie.db")
        MovieDatabase.Schema.create(driver)
        return driver
    }
}

actual fun createDriverSync(factory: DatabaseDriverFactory): SqlDriver {
    return runBlocking { factory.createDriver() }
}