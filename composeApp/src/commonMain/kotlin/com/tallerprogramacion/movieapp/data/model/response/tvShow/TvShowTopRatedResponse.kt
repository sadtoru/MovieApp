package com.tallerprogramacion.movieapp.data.model.response.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowTopRatedResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<TvShowResponse>
)