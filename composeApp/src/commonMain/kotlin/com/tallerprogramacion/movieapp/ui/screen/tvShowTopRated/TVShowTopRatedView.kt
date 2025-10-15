package com.tallerprogramacion.movieapp.ui.screen.tvShowTopRated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tallerprogramacion.movieapp.domain.model.TvShow
import com.tallerprogramacion.movieapp.ui.components.tvShow.TVShowCard

@Composable
fun TVShowTopRatedView(tvShow: List<TvShow>, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "TV Show Top Rated",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.Companion.padding(horizontal = 16.dp),
            color = Color.Companion.White,
            fontWeight = FontWeight.Companion.Bold
        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        LazyRow(
            modifier = Modifier.Companion.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tvShow) { tvShow ->
                TVShowCard(tvShow = tvShow, navController)
            }
        }
    }
}