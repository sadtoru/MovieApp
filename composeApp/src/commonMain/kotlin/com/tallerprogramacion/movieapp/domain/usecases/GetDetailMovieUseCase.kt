package com.tallerprogramacion.movieapp.domain.usecases

import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.MovieDetail
import com.tallerprogramacion.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetDetailMovieUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int): Flow<Resource<MovieDetail>> = movieRepository.getDetailMovie(movieId)

}