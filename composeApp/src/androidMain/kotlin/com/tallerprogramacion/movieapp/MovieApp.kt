package com.tallerprogramacion.movieapp

import android.app.Application
import com.tallerprogramacion.movieapp.di.initKoin
import org.koin.dsl.module

class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(module {
                single<android.content.Context> { this@MovieApp }
            })
        }
    }
}