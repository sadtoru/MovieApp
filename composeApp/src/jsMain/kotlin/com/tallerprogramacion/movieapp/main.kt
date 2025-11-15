package com.tallerprogramacion.movieapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.tallerprogramacion.movieapp.cache.DatabaseDriverFactory
import com.tallerprogramacion.movieapp.cache.initializeJsDriver
import com.tallerprogramacion.movieapp.di.initKoin
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val factory = DatabaseDriverFactory()
    // Initialize the driver before Koin starts
    GlobalScope.launch {
        initializeJsDriver(factory)
        // After driver is initialized, start Koin and the app
        initKoin()
        onWasmReady {
            CanvasBasedWindow(canvasElementId = "ComposeTarget") {
                App()
            }
        }
    }
}