package com.tallerprogramacion.movieapp.ui.screen.personDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.tallerprogramacion.movieapp.ui.base.LoadingType
import com.tallerprogramacion.movieapp.ui.base.ResourceStateHandler
import com.tallerprogramacion.movieapp.ui.components.castDetail.PersonDetailView
import com.tallerprogramacion.movieapp.ui.screen.movie.MovieViewModel

@Composable
fun PersonDetailScreen(
    navController: NavController,
    personId: Int,
    movieViewModel: MovieViewModel
) {

    val personDetailState by movieViewModel.personState.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchDetailPerson(personId = personId)
    }
    ResourceStateHandler(
        resource = personDetailState,
        loadingType = LoadingType.Detail,
        successContent = { person ->

            PersonDetailView(
                person = person,
                movieViewModel = movieViewModel,
                navController = navController
            )

        }

    )
}