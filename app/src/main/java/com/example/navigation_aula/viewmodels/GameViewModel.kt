package com.example.navigation_aula.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.navigation_aula.R
import com.example.navigation_aula.models.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TasksViewModel: ViewModel() {

    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    private var _gameScreenUiState: MutableStateFlow<GameScreenUiState> = MutableStateFlow(
        GameScreenUiState()
    )
    val taskScreenUiState: StateFlow<GameScreenUiState> = _gameScreenUiState.asStateFlow()

    private var _insertEditGameScreenUiState: MutableStateFlow<InsertEditScreenUiState> =
        MutableStateFlow(
            InsertEditScreenUiState()
        )
    val insertEditScreenUiState: StateFlow<InsertEditScreenUiState> =
        _insertEditGameScreenUiState.asStateFlow()

    var editGame: Boolean = false
    var gameToEdit: Game = Game("")


    fun onTaskNameChange(newGameName: String) {
        _insertEditGameScreenUiState.update { currentState ->
            currentState.copy(GameName = newGameName)
        }
    }

    fun onGameReviewChange(newGameReview: String) {
        _insertEditGameScreenUiState.update { currentState ->
            currentState.copy(gameReview = newGameReview)
        }
    }
    fun onGameFavoriteChange(newGameFavorite: Boolean) {
        _insertEditGameScreenUiState.update { currentState ->
            currentState.copy(isFavorite = newGameFavorite)
        }
    }

    fun onGameIsFavoriteChange(updatedGame: Game, newGameCompleted: Boolean) {
        val allGameTemp = _gameScreenUiState.value.allGames.toMutableList()
        var pos = -1
        allGameTemp.forEachIndexed { index, game ->
            if (updatedGame == game) {
                pos = index
            }
        }
        allGameTemp.removeAt(pos)
        allGameTemp.add(pos, updatedGame.copy(isFavorite = newGameCompleted))
        _gameScreenUiState.update { currentState ->
            currentState.copy( allGames = allGameTemp.toList())
        }
    }

    fun RemoveGame(RemovedGame: Game) {
        val gameList = _gameScreenUiState.value.allGames.toMutableList()
        var pos = 0
        gameList.forEachIndexed { index, game ->
            if (RemovedGame == game){
                pos = index
            }
        }
        gameList.removeAt(pos)
        _gameScreenUiState.update { currentState ->
            currentState.copy(allGames = gameList.toList())
        }
    }

    fun showFav() {
        val gameList = _gameScreenUiState.value.allGames.toMutableList()
        val gamesFav = gameList.filter { game ->
            game.isFavorite
        }
        _gameScreenUiState.value = _gameScreenUiState.value.copy(unFiltered = !taskScreenUiState.value.unFiltered, favGames = gamesFav)
    }

    fun navigate(navController: NavController) {
        if (_mainScreenUiState.value.screenName == "Game List") {
            if (editGame) {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Update Game",
                        fabIcon = R.drawable.check,
                    )
                }
            } else {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Insert New Game",
                        fabIcon = R.drawable.check,
                    )
                }
            }
            navController.navigate("insert_edit_game")
        } else {
            _mainScreenUiState.update { currentState ->
                currentState.copy(
                    screenName = "Game List",
                    fabIcon = R.drawable.check,
                )
            }
            if(editGame){
                val allTasksTemp = _gameScreenUiState.value.allGames.toMutableList()
                var pos = -1
                allTasksTemp.forEachIndexed { index, task ->
                    if (gameToEdit == task) {
                        pos = index
                    }
                }
                allTasksTemp.removeAt(pos)
                allTasksTemp.add(pos, gameToEdit.copy(
                    name = _insertEditGameScreenUiState.value.GameName,
                    review = _insertEditGameScreenUiState.value.gameReview,
                    isFavorite = _insertEditGameScreenUiState.value.isFavorite
                ))
                _gameScreenUiState.update { currentState ->
                    currentState.copy(allGames = allTasksTemp.toList())
                }
            }else{
                _gameScreenUiState.update { currentState ->
                    currentState.copy(
                        allGames = currentState.allGames + Game(
                            name = _insertEditGameScreenUiState.value.GameName,
                            review = _insertEditGameScreenUiState.value.gameReview,
                            isFavorite = _insertEditGameScreenUiState.value.isFavorite
                        )
                    )
                }
            }
            _insertEditGameScreenUiState.update {
                InsertEditScreenUiState()
            }
            editGame = true
            gameToEdit = Game("")
            navController.navigate("game_list"){
                popUpTo("game_list"){
                    inclusive = true
                }
            }
        }
    }
    fun editGame(game: Game, navController: NavController){
        editGame = true
        gameToEdit = game
        _insertEditGameScreenUiState.update { currentState ->
            currentState.copy(
                GameName = game.name,
                gameReview = game.review,
                isFavorite = game.isFavorite
            )
        }
        navigate(navController)
    }

    fun onBackPressed(navController: NavController){
        _mainScreenUiState.update { currentState ->
            currentState.copy(
                screenName = "Game List",
                fabIcon = R.drawable.add
            )
        }
        navController.popBackStack()
    }
}