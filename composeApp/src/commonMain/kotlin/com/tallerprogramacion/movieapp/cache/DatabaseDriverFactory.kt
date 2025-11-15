package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    suspend fun createDriver(): SqlDriver
}

expect fun createDriverSync(factory: DatabaseDriverFactory): SqlDriver

