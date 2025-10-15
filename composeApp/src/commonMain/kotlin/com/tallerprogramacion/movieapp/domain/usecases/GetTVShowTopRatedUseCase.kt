package com.tallerprogramacion.movieapp.domain.usecases

import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.TvShow
import com.tallerprogramacion.movieapp.domain.repository.TVShowRepository
import kotlinx.coroutines.flow.Flow

class GetTVShowTopRatedUseCase(
    private val tvRepository: TVShowRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<TvShow>>> = tvRepository.getTVShowTopRated()

}