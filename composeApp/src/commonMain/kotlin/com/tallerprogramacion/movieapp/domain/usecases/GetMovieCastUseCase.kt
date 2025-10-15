package com.tallerprogramacion.movieapp.domain.usecases

import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.Cast
import com.tallerprogramacion.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieCastUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int): Flow<Resource<List<Cast>>> = movieRepository.getMovieCast(movieId)

}