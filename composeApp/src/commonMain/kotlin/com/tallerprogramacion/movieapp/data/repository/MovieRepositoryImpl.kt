package com.tallerprogramacion.movieapp.data.repository

import com.tallerprogramacion.movieapp.cache.LocalDataSource
import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.data.mapper.CastMapper
import com.tallerprogramacion.movieapp.data.mapper.MovieMapper
import com.tallerprogramacion.movieapp.data.mapper.PersonMapper
import com.tallerprogramacion.movieapp.data.model.response.credits.CreditsResponse
import com.tallerprogramacion.movieapp.data.model.response.movie.MovieDetailResponse
import com.tallerprogramacion.movieapp.data.model.response.movie.MovieForActorResponse
import com.tallerprogramacion.movieapp.data.model.response.movie.MovieSimilarResponse
import com.tallerprogramacion.movieapp.data.model.response.movie.PopularMoviesResponse
import com.tallerprogramacion.movieapp.data.model.response.movie.TopRatedMoviesResponse
import com.tallerprogramacion.movieapp.data.model.response.person.PersonResponse
import com.tallerprogramacion.movieapp.data.model.response.video.MovieVideoResponse
import com.tallerprogramacion.movieapp.data.remote.Endpoints
import com.tallerprogramacion.movieapp.data.remote.TMDBApi
import com.tallerprogramacion.movieapp.data.remote.buildUrl
import com.tallerprogramacion.movieapp.domain.model.Cast
import com.tallerprogramacion.movieapp.domain.model.Movie
import com.tallerprogramacion.movieapp.domain.model.MovieDetail
import com.tallerprogramacion.movieapp.domain.model.Person
import com.tallerprogramacion.movieapp.domain.repository.MovieRepository
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val api: TMDBApi,
    private val localDataSource: LocalDataSource
) : MovieRepository {
    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val response: PopularMoviesResponse =
                api.httpClient.get { buildUrl(endpoint = Endpoints.POPULAR_MOVIES) }.body()

            val movieMapper = MovieMapper.toDomainList(response.results)
            
            // Guardar en caché
            localDataSource.saveMovies(movieMapper, "popular_movies")

            emit(Resource.Success(movieMapper))

        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("popular_movies").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached popular movies: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for popular movies")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching movies: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("popular_movies").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached popular movies: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for popular movies")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar películas. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }

    override suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val response: TopRatedMoviesResponse =
                api.httpClient.get { buildUrl(endpoint = Endpoints.TOP_RATED_MOVIES) }.body()

            val movieMapper = MovieMapper.toDomainList(response.results)
            
            // Guardar en caché
            localDataSource.saveMovies(movieMapper, "top_rated_movies")

            emit(Resource.Success(movieMapper))

        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("top_rated_movies").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached top rated movies: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for top rated movies")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching movies: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("top_rated_movies").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached top rated movies: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for top rated movies")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar películas. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }

    override suspend fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading)
        try {
            val response: MovieDetailResponse = api.httpClient.get {
                buildUrl(
                    Endpoints.MOVIE_DETAIL.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            }.body()

            val videoResponse: MovieVideoResponse = api.httpClient.get {
                buildUrl(
                    Endpoints.VIDEO_TRAILER.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            }.body()

            val trailerUrl = videoResponse.results
                .firstOrNull { it.type == "Trailer" && it.site == "YouTube" }?.let {
                    "https://www.youtube.com/embed/${it.key}"
                }

            val movieDetail = MovieMapper.toDomainDetail(response, trailerUrl)
            
            // Guardar en caché
            localDataSource.saveMovieDetail(movieDetail)

            emit(Resource.Success(movieDetail))

        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedDetail = localDataSource.getMovieDetail(movieId)
                if (cachedDetail != null) {
                    Napier.d("Using cached movie detail for movieId: $movieId")
                    emit(Resource.Success(cachedDetail))
                } else {
                    Napier.w("No cached data available for movie detail: $movieId")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching movie detail: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedDetail = localDataSource.getMovieDetail(movieId)
                if (cachedDetail != null) {
                    Napier.d("Using cached movie detail for movieId: $movieId")
                    emit(Resource.Success(cachedDetail))
                } else {
                    Napier.w("No cached data available for movie detail: $movieId")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar detalle de película. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }

    override suspend fun getMovieSimilar(movieId: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val response: MovieSimilarResponse = api.httpClient.get {
                buildUrl(Endpoints.MOVIE_SIMILAR.replace("{movie_id}", movieId.toString()))
            }.body()

            val movieMapper = MovieMapper.toDomainList(response.results)
            
            // Guardar en caché
            localDataSource.saveMovies(movieMapper, "similar_movies_$movieId")

            emit(Resource.Success(movieMapper))
        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("similar_movies_$movieId").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached similar movies: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for similar movies")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching similar movies: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("similar_movies_$movieId").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached similar movies: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for similar movies")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar películas similares. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }

    override suspend fun getMovieCast(movieId: Int): Flow<Resource<List<Cast>>> = flow {
        emit(Resource.Loading)
        try {
            val response: CreditsResponse = api.httpClient.get {
                buildUrl(
                    Endpoints.MOVIE_CREDITS.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            }.body()

            val castMapper = CastMapper.toDomainList(response.cast)
            
            // Guardar en caché
            localDataSource.saveCast(castMapper, movieId)

            emit(Resource.Success(castMapper))

        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedCast = localDataSource.getCast(movieId)
                if (cachedCast.isNotEmpty()) {
                    Napier.d("Using cached cast: ${cachedCast.size} items")
                    emit(Resource.Success(cachedCast))
                } else {
                    Napier.w("No cached data available for cast")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching cast: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedCast = localDataSource.getCast(movieId)
                if (cachedCast.isNotEmpty()) {
                    Napier.d("Using cached cast: ${cachedCast.size} items")
                    emit(Resource.Success(cachedCast))
                } else {
                    Napier.w("No cached data available for cast")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar reparto. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }



    override suspend fun getPersonDetails(personId: Int): Flow<Resource<Person>> = flow {
        emit(Resource.Loading)
        try {
            val response: PersonResponse = api.httpClient.get {
                buildUrl(Endpoints.PERSON_DETAIL.replace("{person_id}", personId.toString()))
            }.body()

            Napier.d { "Respuesta PERSON DETAILS :${response} "}

            val person = PersonMapper.toDomain(response)
            
            // Guardar en caché (sin knownFor ya que no lo cacheamos)
            localDataSource.savePerson(person.copy(knownFor = null))

            emit(Resource.Success(person))
        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedPerson = localDataSource.getPerson(personId)
                if (cachedPerson != null) {
                    Napier.d("Using cached person details for personId: $personId")
                    emit(Resource.Success(cachedPerson))
                } else {
                    Napier.w("No cached data available for person: $personId")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching person details: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedPerson = localDataSource.getPerson(personId)
                if (cachedPerson != null) {
                    Napier.d("Using cached person details for personId: $personId")
                    emit(Resource.Success(cachedPerson))
                } else {
                    Napier.w("No cached data available for person: $personId")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar detalles de persona. ${e.message ?: "Error desconocido"}"
                    }
                    emit(Resource.Error(Exception(errorMessage)))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        }
    }

    override suspend fun getMoviesForActor(personId: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val response: MovieForActorResponse = api.httpClient.get {
                buildUrl(Endpoints.PERSON_MOVIE_CREDITS.replace("{person_id}", personId.toString()))
            }.body()

            val movieMapper = MovieMapper.toDomainMoviesForActor(response.cast ?: emptyList())
            
            // Guardar en caché
            localDataSource.saveMovies(movieMapper, "movies_for_actor_$personId")

            emit(Resource.Success(movieMapper))
        } catch (e: JsonConvertException) {
            Napier.e("JSON conversion error: ${e.message ?: "Unknown error"}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("movies_for_actor_$personId").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached movies for actor: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for movies for actor")
                    emit(Resource.Error(Exception("Error de conexión. No hay datos en caché. ${e.message ?: "Error desconocido"}")))
                }
            } catch (cacheException: Exception) {
                Napier.e("Cache error: ${cacheException.message}")
                emit(Resource.Error(Exception("Error de conexión y caché. ${e.message ?: "Error desconocido"}")))
            }
        } catch (e: Exception) {
            Napier.e("Error fetching movies for actor: ${e.message ?: e::class.simpleName}")
            // Intentar obtener de caché
            try {
                val cachedMovies = localDataSource.getMovies("movies_for_actor_$personId").first()
                if (cachedMovies.isNotEmpty()) {
                    Napier.d("Using cached movies for actor: ${cachedMovies.size} items")
                    emit(Resource.Success(cachedMovies))
                } else {
                    Napier.w("No cached data available for movies for actor")
                    val errorMessage = when {
                        e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                            "Sin conexión a internet. No hay datos en caché."
                        e.message?.contains("timeout", ignoreCase = true) == true -> 
                            "Tiempo de espera agotado. No hay datos en caché."
                        else -> 
                            "Error al cargar películas del actor. ${e.message ?: "Error desconocido"}"
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