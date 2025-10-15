package com.tallerprogramacion.movieapp

import androidx.compose.ui.window.ComposeUIViewController
import com.tallerprogramacion.movieapp.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App() }