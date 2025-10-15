package com.tallerprogramacion.movieapp.ui.components.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerCard() {

    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {

        Card(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .shimmer()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
    }
}