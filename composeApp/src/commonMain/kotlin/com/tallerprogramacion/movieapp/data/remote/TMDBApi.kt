package com.tallerprogramacion.movieapp.data.remote

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient

class TMDBApi(
    val httpClient: HttpClient
) {
    init {
        Napier.base(DebugAntilog())
    }
}