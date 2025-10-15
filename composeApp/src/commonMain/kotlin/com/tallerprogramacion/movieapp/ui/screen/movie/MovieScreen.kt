package com.tallerprogramacion.movieapp.ui.screen.movie

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tallerprogramacion.movieapp.ui.base.LoadingType
import com.tallerprogramacion.movieapp.ui.base.ResourceStateHandler
import com.tallerprogramacion.movieapp.ui.components.movie.MovieCarouselView
import com.tallerprogramacion.movieapp.ui.screen.topRatedMovies.TopRatedMoviesView
import com.tallerprogramacion.movieapp.ui.screen.tvShowTopRated.TVShowTopRatedView
import com.tallerprogramacion.movieapp.ui.screen.tvShowTopRated.TVShowViewModel

@Composable
fun MovieScreen(
    movieViewModel: MovieViewModel,
    tvShowViewModel: TVShowViewModel,
    navController: NavController
) {
    val movieState by movieViewModel.moviesState.collectAsState()
    val topRatedState by movieViewModel.topRatedMoviesState.collectAsState()
    val tvShowTopRatedState by tvShowViewModel.tvShowsState.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchPopularMovies()
        movieViewModel.fetchTopRatedMovies()
        tvShowViewModel.fetchTVShowTopRated()
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(30.dp)) {

        item { Spacer(modifier = Modifier.height(16.dp)) }


        item {
            ResourceStateHandler(
                resource = movieState,
                loadingType = LoadingType.Carrousel,
                successContent = { movies ->
                    MovieCarouselView(movies = movies, navController)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ResourceStateHandler(
                resource = topRatedState,
                loadingType = LoadingType.Card,
                successContent = { movies ->
                    TopRatedMoviesView(movies = movies, navController = navController)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ResourceStateHandler(
                resource = tvShowTopRatedState,
                loadingType = LoadingType.Card,
                successContent = { tvShow ->
                    TVShowTopRatedView(tvShow = tvShow ,navController)
                }
            )
        }

    }

}
