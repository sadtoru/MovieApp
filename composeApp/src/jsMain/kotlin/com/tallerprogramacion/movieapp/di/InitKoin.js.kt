package com.tallerprogramacion.movieapp.di

import com.tallerprogramacion.movieapp.cache.DatabaseDriverFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseDriverFactory() }
}