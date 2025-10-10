package com.tallerprogramacion.movieapp.domain.model

data class TvShow(
    val id: Int,
    val title: String? = null,
    val overview: String?= null,
    val posterUrl: String?= null,
    val releaseDate: String?= null,
    val voteAverage: Double?= null,
    val voteCount: Int?= null,
    val popularity: Double?= null,
    val adult: Boolean?= null
)