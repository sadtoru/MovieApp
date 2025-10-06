package com.tallerprogramacion.movieapp

import android.app.Application
import com.tallerprogramacion.movieapp.di.initKoin

class MovieApp: Application() {
    override fun onCreate() {
        initKoin()
        super.onCreate()
    }
}