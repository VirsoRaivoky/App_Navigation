package com.example.navigation_aula.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.navigation_aula.R
import com.example.navigation_aula.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TasksViewModel: ViewModel() {

    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    private var _taskScreenUiState: MutableStateFlow<TaskScreenUiState> = MutableStateFlow(
        TaskScreenUiState()
    )
    val taskScreenUiState: StateFlow<TaskScreenUiState> = _taskScreenUiState.asStateFlow()

    private var _insertEditTaskScreenUiState: MutableStateFlow<InsertEditScreenUiState> =
        MutableStateFlow(
            InsertEditScreenUiState()
        )
    val insertEditScreenUiState: StateFlow<InsertEditScreenUiState> =
        _insertEditTaskScreenUiState.asStateFlow()

    var editTask: Boolean = false
    var taskToEdit: Task = Task("")
    var toggle: Boolean = false

    fun onTaskNameChange(newTaskName: String) {
        _insertEditTaskScreenUiState.update { currentState ->
            currentState.copy(taskName = newTaskName)
        }
    }

    fun onGameReviewChange(newGameReview: String) {
        _insertEditTaskScreenUiState.update { currentState ->
            currentState.copy(gameReview = newGameReview)
        }
    }
    fun onTaskImportantChange(newTaskImportance: Boolean) {
        _insertEditTaskScreenUiState.update { currentState ->
            currentState.copy(isFavorite = newTaskImportance)
        }
    }

    fun onTaskIsFavoriteChange(updatedTask: Task, newTaskCompleted: Boolean) {
        val allTasksTemp = _taskScreenUiState.value.allTasks.toMutableList()
        var pos = -1
        allTasksTemp.forEachIndexed { index, task ->
            if (updatedTask == task) {
                pos = index
            }
        }
        allTasksTemp.removeAt(pos)
        allTasksTemp.add(pos, updatedTask.copy(isFavorite = newTaskCompleted))
        _taskScreenUiState.update { currentState ->
            currentState.copy(allTasks = allTasksTemp.toList())
        }
    }

    fun RemoveGame( RemovedGame: Task) {
        val gameList = _taskScreenUiState.value.allTasks.toMutableList()
        var pos = 0
        gameList.forEachIndexed { index, task ->
            if (RemovedGame == task){
                pos = index
            }
        }
        gameList.removeAt(pos)
        _taskScreenUiState.update { currentState ->
            currentState.copy(allTasks = gameList.toList())
        }
    }

    fun showFavorite () {
        val gameList = _taskScreenUiState.value.allTasks
        val gamesFav = gameList.filter { task ->
            task.isFavorite
        }
        _taskScreenUiState.update { currentState ->
            currentState.copy(isFiltered = true, allTasks = gamesFav)
        }
    }

    fun navigate(navController: NavController) {
        if (_mainScreenUiState.value.screenName == "Task List") {
            if (editTask) {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Update Task",
                        fabIcon = R.drawable.check,
                    )
                }
            } else {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Insert New Task",
                        fabIcon = R.drawable.check,
                    )
                }
            }
            navController.navigate("insert_edit_task")
        } else {
            _mainScreenUiState.update { currentState ->
                currentState.copy(
                    screenName = "Task List",
                    fabIcon = R.drawable.check,
                )
            }
            if(editTask){
                val allTasksTemp = _taskScreenUiState.value.allTasks.toMutableList()
                var pos = -1
                allTasksTemp.forEachIndexed { index, task ->
                    if (taskToEdit == task) {
                        pos = index
                    }
                }
                allTasksTemp.removeAt(pos)
                allTasksTemp.add(pos, taskToEdit.copy(
                    name = _insertEditTaskScreenUiState.value.taskName,
                    review = _insertEditTaskScreenUiState.value.gameReview,
                    isFavorite = _insertEditTaskScreenUiState.value.isFavorite
                ))
                _taskScreenUiState.update { currentState ->
                    currentState.copy(allTasks = allTasksTemp.toList())
                }
            }else{
                _taskScreenUiState.update { currentState ->
                    currentState.copy(
                        allTasks = currentState.allTasks + Task(
                            name = _insertEditTaskScreenUiState.value.taskName,
                            review = _insertEditTaskScreenUiState.value.gameReview,
                            isFavorite = _insertEditTaskScreenUiState.value.isFavorite
                        )
                    )
                }
            }
            _insertEditTaskScreenUiState.update {
                InsertEditScreenUiState()
            }
            editTask = true
            taskToEdit = Task("")
            navController.navigate("task_list"){
                popUpTo("task_list"){
                    inclusive = true
                }
            }
        }
    }
    fun editTask(task: Task, navController: NavController){
        editTask = true
        taskToEdit = task
        _insertEditTaskScreenUiState.update { currentState ->
            currentState.copy(
                taskName = task.name,
                gameReview = task.review,
                isFavorite = task.isFavorite
            )
        }
        navigate(navController)
    }

    fun onBackPressed(navController: NavController){
        _mainScreenUiState.update { currentState ->
            currentState.copy(
                screenName = "Task List",
                fabIcon = R.drawable.add
            )
        }
        navController.popBackStack()
    }
}