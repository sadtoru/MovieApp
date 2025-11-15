package com.tallerprogramacion.movieapp

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
actual fun currentTimeMillis(): Long = kotlin.js.Date.now().toLong()