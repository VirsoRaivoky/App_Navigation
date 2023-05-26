package com.example.navigation_aula.models

data class Game(
    val name: String = "",
    val review: String = "",
    val isFavorite: Boolean = false,
    var unFiltered: Boolean = true
)
