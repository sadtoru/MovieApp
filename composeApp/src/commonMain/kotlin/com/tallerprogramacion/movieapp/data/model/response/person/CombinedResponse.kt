package com.tallerprogramacion.movieapp.data.model.response.person

import com.tallerprogramacion.movieapp.data.model.response.movie.MovieResponse
import kotlinx.serialization.Serializable

@Serializable
data class CombinedCreditsResponse(
    val cast: List<MovieResponse>
)