package com.tallerprogramacion.movieapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform