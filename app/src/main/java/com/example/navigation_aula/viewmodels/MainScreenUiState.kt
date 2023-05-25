package com.example.navigation_aula.viewmodels

import androidx.annotation.DrawableRes
import com.example.navigation_aula.R

data class MainScreenUiState(
    val screenName: String = "Task List",
    @DrawableRes val fabIcon: Int = R.drawable.add,
    @DrawableRes val favIcon: Int = R.drawable.favorite
)
