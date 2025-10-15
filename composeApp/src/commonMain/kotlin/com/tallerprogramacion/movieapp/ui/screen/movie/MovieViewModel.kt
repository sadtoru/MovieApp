package com.tallerprogramacion.movieapp.ui.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.Cast
import com.tallerprogramacion.movieapp.domain.model.Movie
import com.tallerprogramacion.movieapp.domain.model.MovieDetail
import com.tallerprogramacion.movieapp.domain.model.Person
import com.tallerprogramacion.movieapp.domain.usecases.GetDetailMovieUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetDetailPersonUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetMovieCastUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetMovieSimilarUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetMoviesForActorUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetPopularMoviesUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetTopRatedMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieSimilarUseCase: GetMovieSimilarUseCase,
    private val getDetailPerson: GetDetailPersonUseCase,
    private val getMoviesForActorUseCase: GetMoviesForActorUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesState: StateFlow<Resource<List<Movie>>> get() = _moviesState

    private val _topRatedMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val topRatedMoviesState: StateFlow<Resource<List<Movie>>> get() = _topRatedMoviesState

    private val _movieDetailState = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading)
    val movieDetailState: StateFlow<Resource<MovieDetail>> get() = _movieDetailState

    private val _movieCastState = MutableStateFlow<Resource<List<Cast>>>(Resource.Loading)
    val movieCastState: StateFlow<Resource<List<Cast>>> get() = _movieCastState

    private val _moviesSimilarState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesSimilarState: StateFlow<Resource<List<Movie>>> get() = _moviesSimilarState


    private val _personState = MutableStateFlow<Resource<Person>>(Resource.Loading)
    val personState: StateFlow<Resource<Person>> get() = _personState

    private val _moviesForActorState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesForActorState : StateFlow<Resource<List<Movie>>> = _moviesForActorState

    /**
     * Función genérica para evitar repetición en los fetch
     */
    private fun <T> fetchData(stateFlow: MutableStateFlow<Resource<T>>, useCase: suspend () -> kotlinx.coroutines.flow.Flow<Resource<T>>) {
        viewModelScope.launch {
            useCase().collectLatest { resource ->
                stateFlow.value = resource
            }
        }
    }

    fun fetchPopularMovies() = fetchData(_moviesState) { getPopularMoviesUseCase.invoke() }
    fun fetchTopRatedMovies() = fetchData(_topRatedMoviesState) { getTopRatedMoviesUseCase.invoke() }
    fun fetchMovieDetail(movieId: Int) = fetchData(_movieDetailState) { getDetailMovieUseCase.invoke(movieId) }
    fun fetchMovieCast(movieId: Int) = fetchData(_movieCastState) { getMovieCastUseCase.invoke(movieId) }
    fun fetchMovieSimilar(movieId: Int) = fetchData(_moviesSimilarState) { getMovieSimilarUseCase.invoke(movieId) }
    fun fetchDetailPerson(personId: Int) = fetchData(_personState) { getDetailPerson.invoke(personId) }
    fun fetchMoviesForActor(personId: Int) = fetchData(_moviesForActorState) { getMoviesForActorUseCase.invoke(personId) }

}