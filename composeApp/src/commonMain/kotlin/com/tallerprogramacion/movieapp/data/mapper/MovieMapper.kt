package com.tallerprogramacion.movieapp.data.mapper

import com.tallerprogramacion.movieapp.data.core.Constants.NOT_AVAILABLE
import com.tallerprogramacion.movieapp.data.model.response.movie.MovieDetailResponse
import com.tallerprogramacion.movieapp.data.model.response.movie.MovieResponse
import com.tallerprogramacion.movieapp.domain.model.Movie
import com.tallerprogramacion.movieapp.domain.model.MovieDetail

object MovieMapper {
    private fun toDomain(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id,
            title = movieResponse.title ?: NOT_AVAILABLE,
            overview = movieResponse.overview ?: NOT_AVAILABLE,
            posterUrl = "https://image.tmdb.org/t/p/w500${movieResponse.posterPath}" ?: NOT_AVAILABLE,
            releaseDate = movieResponse.releaseDate ?: NOT_AVAILABLE,
            voteAverage = movieResponse.voteAverage?: 0.0,
            voteCount = movieResponse.voteCount ?: 0,
            popularity = movieResponse.popularity?: 0.0,
            adult = movieResponse.adult ?: false,
        )
    }

    fun toDomainList(movieResponseList: List<MovieResponse>): List<Movie> = movieResponseList.map { toDomain(it) }


    fun toDomainDetail(movieDetailResponse: MovieDetailResponse, trailerUrl: String?=null): MovieDetail {
        return MovieDetail(
            id = movieDetailResponse.id,
            title = movieDetailResponse.title,
            overview = movieDetailResponse.overview,
            posterUrl = "https://image.tmdb.org/t/p/w500${movieDetailResponse.posterPath}",
            backdropUrl = "https://image.tmdb.org/t/p/w500${movieDetailResponse.backdropPath}",
            releaseDate = movieDetailResponse.releaseDate,
            voteAverage = movieDetailResponse.voteAverage,
            voteCount = movieDetailResponse.voteCount,
            popularity = movieDetailResponse.popularity,
            adult = movieDetailResponse.adult,
            collection = movieDetailResponse.belongsToCollection?.let {
                MovieDetail.Collection(
                    id = it.id,
                    name = it.name,
                    posterPath = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                    backdropPath = "https://image.tmdb.org/t/p/w500${it.backdropPath}"
                )
            },
            budget = movieDetailResponse.budget,
            revenue = movieDetailResponse.revenue,
            runtime = movieDetailResponse.runtime,
            trailerUrl = trailerUrl
        )
    }

    fun toDomainMoviesForActor(cast: List<MovieResponse>): List<Movie> {
        return cast.map { toDomain(it) }
    }

}