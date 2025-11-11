package com.tallerprogramacion.movieapp.di

import com.tallerprogramacion.movieapp.cache.DatabaseDriverFactory
import com.tallerprogramacion.movieapp.cache.LocalDataSource
import com.tallerprogramacion.movieapp.cache.MovieDatabase
import com.tallerprogramacion.movieapp.createHttpClient
import com.tallerprogramacion.movieapp.data.remote.TMDBApi
import com.tallerprogramacion.movieapp.data.repository.MovieRepositoryImpl
import com.tallerprogramacion.movieapp.data.repository.TVShowRepositoryImpl
import com.tallerprogramacion.movieapp.domain.repository.MovieRepository
import com.tallerprogramacion.movieapp.domain.repository.TVShowRepository
import com.tallerprogramacion.movieapp.domain.usecases.GetDetailMovieUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetDetailPersonUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetMovieCastUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetMovieSimilarUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetMoviesForActorUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetPopularMoviesUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetTVShowTopRatedUseCase
import com.tallerprogramacion.movieapp.domain.usecases.GetTopRatedMoviesUseCase
import com.tallerprogramacion.movieapp.ui.screen.movie.MovieViewModel
import com.tallerprogramacion.movieapp.ui.screen.tvShowTopRated.TVShowViewModel
import io.ktor.client.HttpClient
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val cacheModule = module {
    single { MovieDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single { LocalDataSource(get()) }
}

private val dataModule = module {
    single<HttpClient> { createHttpClient() }
    single { TMDBApi(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<TVShowRepository> { TVShowRepositoryImpl(get(), get()) }

}

private val domainModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetTopRatedMoviesUseCase(get()) }
    factory { GetTVShowTopRatedUseCase(get()) }
    factory { GetDetailMovieUseCase(get()) }
    factory { GetMovieCastUseCase(get()) }
    factory { GetMovieSimilarUseCase(get()) }
    factory { GetDetailPersonUseCase(get()) }
    factory { GetMoviesForActorUseCase(get()) }

}

private val viewModelModule = module {
    viewModel { MovieViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { TVShowViewModel(get()) }
}


var sharedModules = listOf(cacheModule, domainModule, dataModule, viewModelModule)