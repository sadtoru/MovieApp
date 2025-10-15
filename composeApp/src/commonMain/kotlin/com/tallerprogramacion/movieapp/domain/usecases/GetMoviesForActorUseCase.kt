package com.tallerprogramacion.movieapp.domain.usecases

import com.tallerprogramacion.movieapp.domain.repository.MovieRepository

class GetMoviesForActorUseCase(private val repository: MovieRepository) {
    suspend fun invoke(personId:Int) = repository.getMoviesForActor(personId)
}