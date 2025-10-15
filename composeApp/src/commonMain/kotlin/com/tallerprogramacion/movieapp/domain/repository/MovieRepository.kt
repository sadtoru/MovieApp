package com.tallerprogramacion.movieapp.domain.repository

import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.Cast
import com.tallerprogramacion.movieapp.domain.model.Movie
import com.tallerprogramacion.movieapp.domain.model.MovieDetail
import com.tallerprogramacion.movieapp.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMovies():Flow<Resource<List<Movie>>>
    suspend fun getDetailMovie(movieId:Int):Flow<Resource<MovieDetail>>
    suspend fun getMovieCast(movieId: Int): Flow<Resource<List<Cast>>>
    suspend fun getMovieSimilar(movieId: Int):Flow<Resource<List<Movie>>>
    suspend fun getPersonDetails(personId:Int):Flow<Resource<Person>>
    suspend fun getMoviesForActor(personId:Int):Flow<Resource<List<Movie>>>
}