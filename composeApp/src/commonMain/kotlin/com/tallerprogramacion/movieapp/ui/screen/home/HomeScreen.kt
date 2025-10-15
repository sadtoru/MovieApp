package com.tallerprogramacion.movieapp.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tallerprogramacion.movieapp.ui.screen.movie.MovieScreen
import com.tallerprogramacion.movieapp.ui.screen.movie.MovieViewModel
import com.tallerprogramacion.movieapp.ui.screen.tvShowTopRated.TVShowViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    movieViewModel: MovieViewModel,
    tvShowViewModel: TVShowViewModel
) {
    MovieScreen(movieViewModel,tvShowViewModel,navController)
}