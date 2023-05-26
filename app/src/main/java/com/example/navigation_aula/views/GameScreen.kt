package com.example.navigation_aula.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation_aula.models.Game
import com.example.navigation_aula.viewmodels.TasksViewModel

@Composable
fun GameScreen(
    navController: NavController,
    tasksViewModel: TasksViewModel
) {

    val uiState by tasksViewModel.taskScreenUiState.collectAsState()

    gameList(
        games = if(uiState.unFiltered) uiState.allGames else uiState.favGames,
        onFavoriteChange = { game, isCompleted -> tasksViewModel.onGameIsFavoriteChange(game, isCompleted)},
        onEditGame = {tasksViewModel.editGame(it, navController)}
    ) { tasksViewModel.RemoveGame(it) }
}

@Composable
fun gameList(
    games: List<Game>,
    onFavoriteChange: (Game, Boolean) -> Unit,
    onEditGame: (Game) -> Unit,
    RemoveGame: (Game) -> Unit
) {
    LazyColumn(){
        items(games){ game ->
            GameEntry(game = game, onFavoriteChange = { onFavoriteChange(game, it)}, onEditGame = {onEditGame(it)},RemoveGame = {RemoveGame(game)
            })
        }
    }
}

@Composable
fun GameEntry(
    game: Game,
    onFavoriteChange: (Boolean) -> Unit,
    onEditGame: () -> Unit,
    RemoveGame: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onEditGame() }, elevation  = 4.dp,
        backgroundColor = Color.LightGray,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
        ){
            Row() {
                if(game.isFavorite){
                    Icon(painter = painterResource(id = com.example.navigation_aula.R.drawable.favorite), contentDescription = "high priority")
                }
                Text(text = game.name,
                color = Color.Black)

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
                Checkbox(checked = game.isFavorite, onCheckedChange = onFavoriteChange)
            }
        }
    }
}