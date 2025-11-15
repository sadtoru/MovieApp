package com.tallerprogramacion.movieapp.data.repository

import com.tallerprogramacion.movieapp.cache.LocalDataSource
import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.data.mapper.TVShowMapper
import com.tallerprogramacion.movieapp.data.model.response.tvShow.TvShowTopRatedResponse
import com.tallerprogramacion.movieapp.data.remote.Endpoints
import com.tallerprogramacion.movieapp.data.remote.TMDBApi
import com.tallerprogramacion.movieapp.data.remote.buildUrl
import com.tallerprogramacion.movieapp.domain.model.TvShow
import com.tallerprogramacion.movieapp.domain.repository.TVShowRepository
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class TVShowRepositoryImpl(
    private val api: TMDBApi,
    private val localDataSource: LocalDataSource
): TVShowRepository{
    override suspend fun getTVShowTopRated(): Flow<Resource<List<TvShow>>> = flow{
        emit(Resource.Loading)
        try {
            // Fetch the full response with the results
            val response: TvShowTopRatedResponse = api.httpClient.get { buildUrl(endpoint = Endpoints.TOP_RATED_TV_SHOW) }.body()

            val tvMapper = TVShowMapper.toDomainList(response.results)
            
            // Guardar en caché
            localDataSource.saveTvShows(tvMapper, "top_rated_tv_shows")

            emit(Resource.Success(tvMapper))

        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedTvShows = localDataSource.getTvShows("top_rated_tv_shows").first()
                if (cachedTvShows.isNotEmpty()) {
                    Napier.d("Using cached TV shows: ${cachedTvShows.size} items")
                    emit(Resource.Success(cachedTvShows))
                } else {
                    Napier.w("No cached data available for TV shows")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching tv: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedTvShows = localDataSource.getTvShows("top_rated_tv_shows").first()
                if (cachedTvShows.isNotEmpty()) {
                    Napier.d("Using cached TV shows: ${cachedTvShows.size} items")
                    emit(Resource.Success(cachedTvShows))
                } else {
                    Napier.w("No cached data available for TV shows")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar series. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }

}