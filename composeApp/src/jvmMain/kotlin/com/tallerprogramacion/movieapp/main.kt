package com.tallerprogramacion.movieapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tallerprogramacion.movieapp.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "MovieApp",
    ) {
        App()
    }
}