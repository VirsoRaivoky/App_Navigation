package com.example.navigation_aula.viewmodels

import com.example.navigation_aula.models.Game

data class GameScreenUiState(

    val allGames: List<Game> = listOf(
        Game("Ultrakill","Jogo Bom", isFavorite = false),
        Game("Rust","Superestimado", isFavorite = false),
        Game("Unturned","mal Otimizado", isFavorite = false),
        Game("TLOU", "Ok", isFavorite = true)
    ),
    val favGames: List<Game> = listOf(),
    var unFiltered: Boolean = true
)



