package com.tallerprogramacion.movieapp.domain.repository

import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TVShowRepository {
    suspend fun getTVShowTopRated(): Flow<Resource<List<TvShow>>>

}