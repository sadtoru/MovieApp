package com.tallerprogramacion.movieapp.data.remote

import com.tallerprogramacion.movieapp.BuildKonfig
import com.tallerprogramacion.movieapp.data.core.Constants
import io.github.aakira.napier.Napier
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

fun HttpRequestBuilder.buildUrl(endpoint: String, queryParams: Map<String, String> = emptyMap()) {
    val apiKey = BuildKonfig.TMDB_API_KEY

    if (apiKey == "missing api key" || apiKey.isBlank()) {
        Napier.e("ERROR: TMDB_API_KEY no está configurada. Por favor, agrega TMDB_API_KEY=tu_api_key en el archivo local.properties")
    } else {
        Napier.d("Using API Key: ${apiKey.take(8)}...")
    }
    
    url {
        takeFrom(Endpoints.BASE_URL)
        appendPathSegments(endpoint)
        parameters.append(Constants.API_KEY, apiKey)
        queryParams.forEach { (key, value) ->
            parameters.append(key, value)
        }
    }
    accept(ContentType.Application.Json)
}