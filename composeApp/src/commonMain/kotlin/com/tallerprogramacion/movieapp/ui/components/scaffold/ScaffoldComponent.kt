package com.tallerprogramacion.movieapp.ui.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun ScaffoldComponent(
    navController: NavController,
    title: String,
    content: @Composable () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBarComponent(title = title)
        },

        bottomBar = {
            BottomNavigationBar(navController)
        },
        contentColor = Color.Transparent
    ) { innerPadding ->


        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}