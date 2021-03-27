package com.example.taboogame.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taboogame.models.NewGameSettings

class GameViewModelFactory(
    private val newGameSettings: NewGameSettings
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(newGameSettings) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}