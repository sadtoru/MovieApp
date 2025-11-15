package com.tallerprogramacion.movieapp.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.tallerprogramacion.movieapp.cache.MovieDatabase
import com.tallerprogramacion.movieapp.currentTimeMillis
import com.tallerprogramacion.movieapp.domain.model.Cast
import com.tallerprogramacion.movieapp.domain.model.Movie
import com.tallerprogramacion.movieapp.domain.model.MovieDetail
import com.tallerprogramacion.movieapp.domain.model.Person
import com.tallerprogramacion.movieapp.domain.model.TvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val database: MovieDatabase) {

    // Movie
    suspend fun saveMovies(movies: List<Movie>, cacheKey: String) {
        val cachedAt = currentTimeMillis()
        database.transaction {
            database.movieQueries.deleteMoviesByCacheKey(cacheKey)
            movies.forEach { movie ->
                database.movieQueries.insertMovie(
                    id = movie.id.toLong(),
                    title = movie.title,
                    overview = movie.overview,
                    poster_url = movie.posterUrl,
                    release_date = movie.releaseDate,
                    vote_average = movie.voteAverage,
                    vote_count = movie.voteCount?.toLong(),
                    popularity = movie.popularity,
                    adult = if (movie.adult == true) 1L else 0L,
                    trailer_url = movie.trailerUrl,
                    cache_key = cacheKey,
                    cached_at = cachedAt
                )
            }
        }
    }

    fun getMovies(cacheKey: String): Flow<List<Movie>> {
        return database.movieQueries.selectMoviesByCacheKey(cacheKey)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { movies ->
                movies.map { movie ->
                    Movie(
                        id = movie.id.toInt(),
                        title = movie.title,
                        overview = movie.overview,
                        posterUrl = movie.poster_url,
                        releaseDate = movie.release_date,
                        voteAverage = movie.vote_average,
                        voteCount = movie.vote_count?.toInt(),
                        popularity = movie.popularity,
                        adult = movie.adult == 1L,
                        trailerUrl = movie.trailer_url
                    )
                }
            }
    }

    // MovieDetail
    suspend fun saveMovieDetail(movieDetail: MovieDetail) {
        val cachedAt = currentTimeMillis()
        database.movieDetailQueries.insertMovieDetail(
            id = movieDetail.id.toLong(),
            title = movieDetail.title,
            overview = movieDetail.overview,
            poster_url = movieDetail.posterUrl,
            backdrop_url = movieDetail.backdropUrl,
            release_date = movieDetail.releaseDate,
            vote_average = movieDetail.voteAverage,
            vote_count = movieDetail.voteCount?.toLong(),
            popularity = movieDetail.popularity,
            adult = if (movieDetail.adult == true) 1L else 0L,
            budget = movieDetail.budget?.toLong(),
            revenue = movieDetail.revenue?.toLong(),
            runtime = movieDetail.runtime?.toLong(),
            trailer_url = movieDetail.trailerUrl,
            collection_id = movieDetail.collection?.id?.toLong(),
            collection_name = movieDetail.collection?.name,
            collection_poster_path = movieDetail.collection?.posterPath,
            collection_backdrop_path = movieDetail.collection?.backdropPath,
            cached_at = cachedAt
        )
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetail? {
        return database.movieDetailQueries.selectMovieDetailById(movieId.toLong())
            .executeAsOneOrNull()
            ?.let { detail ->
                MovieDetail(
                    id = detail.id.toInt(),
                    title = detail.title,
                    overview = detail.overview,
                    posterUrl = detail.poster_url,
                    backdropUrl = detail.backdrop_url,
                    releaseDate = detail.release_date,
                    voteAverage = detail.vote_average,
                    voteCount = detail.vote_count?.toInt(),
                    popularity = detail.popularity,
                    adult = detail.adult == 1L,
                    collection = detail.collection_id?.let { id ->
                        MovieDetail.Collection(
                            id = id.toInt(),
                            name = detail.collection_name,
                            posterPath = detail.collection_poster_path,
                            backdropPath = detail.collection_backdrop_path
                        )
                    },
                    budget = detail.budget?.toInt(),
                    revenue = detail.revenue?.toInt(),
                    runtime = detail.runtime?.toInt(),
                    trailerUrl = detail.trailer_url
                )
            }
    }

    // Cast
    suspend fun saveCast(cast: List<Cast>, movieId: Int) {
        val cachedAt = currentTimeMillis()
        database.transaction {
            database.castQueries.deleteCastByMovieId(movieId.toLong())
            cast.forEach { castMember ->
                database.castQueries.insertCast(
                    id = castMember.id.toLong(),
                    name = castMember.name,
                    character = castMember.character,
                    profile_path = castMember.profilePath,
                    movie_id = movieId.toLong(),
                    cached_at = cachedAt
                )
            }
        }
    }

    suspend fun getCast(movieId: Int): List<Cast> {
        return database.castQueries.selectCastByMovieId(movieId.toLong())
            .executeAsList()
            .map { cast ->
                Cast(
                    id = cast.id.toInt(),
                    name = cast.name,
                    character = cast.character,
                    profilePath = cast.profile_path
                )
            }
    }

    // Person
    suspend fun savePerson(person: Person) {
        val cachedAt = currentTimeMillis()
        database.personQueries.insertPerson(
            id = person.id.toLong(),
            name = person.name,
            birthday = person.birthday,
            place_of_birth = person.placeOfBirth,
            biography = person.biography,
            profile_path = person.profilePath,
            cached_at = cachedAt
        )
    }

    suspend fun getPerson(personId: Int): Person? {
        return database.personQueries.selectPersonById(personId.toLong())
            .executeAsOneOrNull()
            ?.let { person ->
                Person(
                    id = person.id.toInt(),
                    name = person.name,
                    birthday = person.birthday,
                    placeOfBirth = person.place_of_birth,
                    biography = person.biography,
                    profilePath = person.profile_path,
                    knownFor = null
                )
            }
    }

    // TvShow
    suspend fun saveTvShows(tvShows: List<TvShow>, cacheKey: String) {
        val cachedAt = currentTimeMillis()
        database.transaction {
            database.tvShowQueries.deleteTvShowsByCacheKey(cacheKey)
            tvShows.forEach { tvShow ->
                database.tvShowQueries.insertTvShow(
                    id = tvShow.id.toLong(),
                    title = tvShow.title,
                    overview = tvShow.overview,
                    poster_url = tvShow.posterUrl,
                    release_date = tvShow.releaseDate,
                    vote_average = tvShow.voteAverage,
                    vote_count = tvShow.voteCount?.toLong(),
                    popularity = tvShow.popularity,
                    adult = if (tvShow.adult == true) 1L else 0L,
                    cache_key = cacheKey,
                    cached_at = cachedAt
                )
            }
        }
    }

    fun getTvShows(cacheKey: String): Flow<List<TvShow>> {
        return database.tvShowQueries.selectTvShowsByCacheKey(cacheKey)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { tvShows ->
                tvShows.map { tvShow ->
                    TvShow(
                        id = tvShow.id.toInt(),
                        title = tvShow.title,
                        overview = tvShow.overview,
                        posterUrl = tvShow.poster_url,
                        releaseDate = tvShow.release_date,
                        voteAverage = tvShow.vote_average,
                        voteCount = tvShow.vote_count?.toInt(),
                        popularity = tvShow.popularity,
                        adult = tvShow.adult == 1L
                    )
                }
            }
    }
}

