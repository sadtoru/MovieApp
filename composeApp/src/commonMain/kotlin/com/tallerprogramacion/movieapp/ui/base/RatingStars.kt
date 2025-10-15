package com.tallerprogramacion.movieapp.ui.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tallerprogramacion.movieapp.ui.themes.starColor

@Composable
fun RatingStars(voteAverage: Double) {
    val filledStars = (voteAverage / 2).toInt()
    val hasHalfStar = (voteAverage / 2) % 1 >= 0.5
    val starColor = starColor

    Row {

        repeat(filledStars) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Star",
                tint = starColor,
                modifier = Modifier.size(16.dp)
            )
        }


        if (hasHalfStar) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = "Half Star",
                tint = starColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}