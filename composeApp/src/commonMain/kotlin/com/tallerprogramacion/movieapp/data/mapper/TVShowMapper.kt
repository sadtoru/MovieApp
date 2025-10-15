package com.tallerprogramacion.movieapp.data.mapper

import com.tallerprogramacion.movieapp.data.model.response.tvShow.TvShowResponse
import com.tallerprogramacion.movieapp.domain.model.TvShow

object TVShowMapper {
    private fun toDomain(tvShowResponse: TvShowResponse): TvShow {
        return TvShow(
            id = tvShowResponse.id,
            title = tvShowResponse.title, // El título puede estar en 'name' para TV Shows
            overview = tvShowResponse.overview,
            posterUrl = "https://image.tmdb.org/t/p/w500${tvShowResponse.posterPath}",
            releaseDate = tvShowResponse.firstAirDate,
            voteAverage = tvShowResponse.voteAverage,
            voteCount = tvShowResponse.voteCount,
            popularity = tvShowResponse.popularity,
            adult = tvShowResponse.adult
        )
    }

    fun toDomainList(tvShowResponseList: List<TvShowResponse>): List<TvShow> = tvShowResponseList.map { toDomain(it) }
}