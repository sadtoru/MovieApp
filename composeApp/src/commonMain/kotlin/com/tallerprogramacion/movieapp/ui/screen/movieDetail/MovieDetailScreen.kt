package com.tallerprogramacion.movieapp.ui.screen.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.tallerprogramacion.movieapp.ui.base.LoadingType
import com.tallerprogramacion.movieapp.ui.base.ResourceStateHandler
import com.tallerprogramacion.movieapp.ui.components.movieDetail.MovieDetailView
import com.tallerprogramacion.movieapp.ui.screen.movie.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    val movieDetailState by movieViewModel.movieDetailState.collectAsState()
    val movieCastState by movieViewModel.movieCastState.collectAsState()
    val movieSimilarState by movieViewModel.moviesSimilarState.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchMovieDetail(movieId)
        movieViewModel.fetchMovieCast(movieId)
        movieViewModel.fetchMovieSimilar(movieId)
    }

    ResourceStateHandler(
        resource = movieDetailState,
        loadingType = LoadingType.Detail,
        successContent = { movie ->
            ResourceStateHandler(
                resource = movieCastState,
                loadingType = LoadingType.Detail,
                successContent = { cast ->
                    ResourceStateHandler(
                        resource = movieSimilarState,
                        loadingType = LoadingType.Detail,
                        successContent = { similarMovies ->
                            MovieDetailView(
                                movie = movie,
                                cast = cast,
                                movieSimilar = similarMovies,
                                navController = navController,
                            )
                        }
                    )
                }
            )
        }
    )
}