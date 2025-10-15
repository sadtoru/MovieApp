package com.tallerprogramacion.movieapp.data.remote

import com.tallerprogramacion.movieapp.createHttpClient
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient

class TMDBApi {
    val httpClient: HttpClient by lazy { createHttpClient() }

    init {
        Napier.base(DebugAntilog())
    }
}