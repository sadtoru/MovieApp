package com.tallerprogramacion.movieapp.di

import com.tallerprogramacion.movieapp.cache.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
}

