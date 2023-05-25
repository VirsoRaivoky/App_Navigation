package com.example.navigation_aula.viewmodels

import com.example.navigation_aula.models.Task

data class TaskScreenUiState(
    val allTasks: List<Task> = listOf(
        Task("Ultrakill","Jogo Bom", isFavorite = false),
        Task("Rust","Superestimado", isFavorite = false),
        Task("Unturned","mal Otimizado", isFavorite = false, ),
    )
)

