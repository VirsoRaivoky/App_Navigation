package com.example.navigation_aula.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.navigation_aula.models.Task
import com.example.navigation_aula.viewmodels.TasksViewModel

@Composable
fun TaskScreen(
    navController: NavController,
    tasksViewModel: TasksViewModel
) {

    val uiState by tasksViewModel.taskScreenUiState.collectAsState()

    taskList(
        tasks = uiState.allTasks,
        onCompletedChange = { task, isCompleted -> tasksViewModel.onTaskIsFavoriteChange(task, isCompleted)},
        onEditTask = {tasksViewModel.editTask(it, navController)},
        RemoveGame = {tasksViewModel.RemoveGame(it)}
    )
}

@Composable
fun taskList(
    tasks: List<Task>,
    onCompletedChange: (Task, Boolean) -> Unit,
    onEditTask: (Task) -> Unit,
    RemoveGame: (Task) -> Unit
) {
    LazyColumn(){
        items(tasks){ task ->
            TaskEntry(task = task, onCompletedChange = {onCompletedChange(task, it)}, onEditTask = {onEditTask(task)}, RemoveGame = {RemoveGame(task)})
        }
    }
}

@Composable
fun TaskEntry(
    tasksViewModel: TasksViewModel = viewModel(),
    task: Task,
    onCompletedChange: (Boolean) -> Unit,
    onEditTask: () -> Unit,
    RemoveGame: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onEditTask() }, elevation  = 4.dp,
        backgroundColor = Color.Blue,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
        ){
            Row() {
                if(task.isFavorite){
                    Icon(painter = painterResource(id = com.example.navigation_aula.R.drawable.high_priority), contentDescription = "high priority")
                }
                Text(text = task.name,
                color = Color.White)

            }
            Row(horizontalArrangement = Arrangement.Center) {
                IconButton(onClick = {
                    RemoveGame()
                }
                ){
                    Icon(
                        painter = painterResource(id = com.example.navigation_aula.R.drawable.remove),
                        contentDescription = "remove Button"
                    )
                }
                Checkbox(checked = task.isFavorite, onCheckedChange = onCompletedChange)
            }
        }
    }
}