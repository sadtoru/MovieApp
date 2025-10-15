package com.tallerprogramacion.movieapp.domain.usecases

import com.tallerprogramacion.movieapp.domain.repository.MovieRepository

class GetDetailPersonUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(personId:Int) = repository.getPersonDetails(personId)
}