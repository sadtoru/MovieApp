package com.tallerprogramacion.movieapp.data.remote

import com.tallerprogramacion.movieapp.BuildKonfig
import com.tallerprogramacion.movieapp.data.core.Constants
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

fun HttpRequestBuilder.buildUrl(endpoint: String, queryParams: Map<String, String> = emptyMap()) {
    url {
        takeFrom(Endpoints.BASE_URL)
        appendPathSegments(endpoint)
        parameters.append(Constants.API_KEY, BuildKonfig.TMDB_API_KEY)
        queryParams.forEach { (key, value) ->
            parameters.append(key, value)
        }
    }
    accept(ContentType.Application.Json)
}