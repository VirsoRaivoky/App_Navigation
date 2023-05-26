package com.example.navigation_aula.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation_aula.viewmodels.TasksViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    tasksViewModel: TasksViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by tasksViewModel.mainScreenUiState.collectAsState()
    var toggle: Boolean = false

    Scaffold(
        floatingActionButton = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                FloatingActionButton( onClick = {
                    tasksViewModel.showFav()
                    toggle = !toggle
                }
                ) {
                    Icon(painter = painterResource(id = uiState.favIcon), contentDescription = null)
                }

                FloatingActionButton( onClick = {
                    tasksViewModel.navigate(navController)
                }) {
                    Icon(painter = painterResource(id = uiState.fabIcon), contentDescription = null)
                }
            }
        },

        topBar = {
            TopAppBar(backgroundColor = Color.Gray) {
                Text(text = uiState.screenName,
                color = Color.DarkGray)
            }
        }

    )
    {
        NavHost(navController = navController, startDestination = "game_list"){
            composable("game_list"){
                GameScreen(navController = navController, tasksViewModel = tasksViewModel)
            }
            composable("insert_edit_game"){
                InsertEditGameScreen(navController = navController, tasksViewModel = tasksViewModel )
            }
        }
    }
}