package com.example.navigation_aula.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
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
    var toggle: Boolean = false
    val uiState by tasksViewModel.mainScreenUiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                FloatingActionButton( onClick = {
                    if(!toggle){
                        tasksViewModel.showFavorite()
                        toggle = true
                    }else {
                        tasksViewModel.showList()
                        toggle = false
                    }
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
            TopAppBar(backgroundColor = Color.Magenta) {
                Text(text = uiState.screenName,
                color = Color.White)
            }
        }


    )
    {
        NavHost(navController = navController, startDestination = "task_list"){
            composable("task_list"){
                TaskScreen(navController = navController, tasksViewModel = tasksViewModel)
            }
            composable("insert_edit_task"){
                InsertEditTaskScreen(navController = navController, tasksViewModel = tasksViewModel )
            }
        }
    }
}