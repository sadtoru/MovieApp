package com.tallerprogramacion.movieapp.data.model.response.movie

import kotlinx.serialization.Serializable

@Serializable
data class MovieForActorResponse(
    val cast: List<MovieResponse>? = null
)