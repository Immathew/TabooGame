package com.example.taboogame.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(private val roundTime: Long,
                           private val skipAvailable: Int,
                           private val pointsLimit: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(roundTime, skipAvailable, pointsLimit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}