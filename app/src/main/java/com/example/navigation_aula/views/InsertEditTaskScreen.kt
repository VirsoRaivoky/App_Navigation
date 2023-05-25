package com.example.navigation_aula.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.navigation_aula.viewmodels.TasksViewModel

@Composable
fun InsertEditTaskScreen(
    navController: NavController,
    tasksViewModel: TasksViewModel
) {
    val uiState by tasksViewModel.insertEditScreenUiState.collectAsState()
    BackHandler {
        tasksViewModel.onBackPressed(navController)
    }
    InsertEditForm(
        name = uiState.taskName,
        review = uiState.gameReview,
        important = uiState.isFavorite,
        onReviewChange = {tasksViewModel.onGameReviewChange(it)},
        onNameChange = {tasksViewModel.onTaskNameChange(it)},
        onImportantChange = {tasksViewModel.onTaskImportantChange(it)},
        )

}

@Composable
fun InsertEditForm(
    name: String,
    review: String,
    important: Boolean,
    onNameChange: (String) -> Unit,
    onReviewChange: (String) -> Unit,
    onImportantChange: (Boolean) -> Unit,

) {
    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            OutlinedTextField(
                label = { Text(text = "Task Name")},
                value = name, onValueChange = onNameChange
            )
            OutlinedTextField(
                label = { Text(text = "Review")},
                value = review, onValueChange = onReviewChange
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = important, onCheckedChange = onImportantChange)
                Text(text = "Important Task")

            }
        }
    }
}