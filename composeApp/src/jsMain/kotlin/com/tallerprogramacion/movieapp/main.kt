package com.tallerprogramacion.movieapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.tallerprogramacion.movieapp.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onWasmReady {
        CanvasBasedWindow(canvasElementId = "ComposeTarget") {
            App()
        }
    }
}