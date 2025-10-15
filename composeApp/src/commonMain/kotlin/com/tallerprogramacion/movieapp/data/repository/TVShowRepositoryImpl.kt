package com.tallerprogramacion.movieapp.data.repository

import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.data.mapper.TVShowMapper
import com.tallerprogramacion.movieapp.data.model.response.tvShow.TvShowTopRatedResponse
import com.tallerprogramacion.movieapp.data.remote.Endpoints
import com.tallerprogramacion.movieapp.data.remote.TMDBApi
import com.tallerprogramacion.movieapp.data.remote.buildUrl
import com.tallerprogramacion.movieapp.domain.model.TvShow
import com.tallerprogramacion.movieapp.domain.repository.TVShowRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TVShowRepositoryImpl(private val api: TMDBApi): TVShowRepository{
    override suspend fun getTVShowTopRated(): Flow<Resource<List<TvShow>>> = flow{
        emit(Resource.Loading)
        try {
            // Fetch the full response with the results
            val response: TvShowTopRatedResponse = api.httpClient.get { buildUrl(endpoint = Endpoints.TOP_RATED_TV_SHOW) }.body()

            val tvMapper = TVShowMapper.toDomainList(response.results)

            emit(Resource.Success(tvMapper))

        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching tv: ${e.message}")))
        }
    }

}