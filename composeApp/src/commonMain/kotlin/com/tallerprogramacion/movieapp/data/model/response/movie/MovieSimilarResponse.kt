package com.tallerprogramacion.movieapp.data.model.response.movie

import kotlinx.serialization.Serializable

@Serializable
data class MovieSimilarResponse(
    val results: List<MovieResponse>
)