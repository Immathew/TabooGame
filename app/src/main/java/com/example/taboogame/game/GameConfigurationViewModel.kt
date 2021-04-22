package com.example.taboogame.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.taboogame.models.NewGameSettings
import com.example.taboogame.repo.SharedPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameConfigurationViewModel @Inject constructor(
    private val sharedPreferencesRepo: SharedPreferencesRepo,
    application: Application) : AndroidViewModel(application) {

    var newGameSettings = NewGameSettings(
        60000L, 3, 20, "Team 1", "Team 2", false, ""
    )

    fun setLanguageAndVibration() {
        newGameSettings.language = sharedPreferencesRepo.readGuessWordsLanguageSettings()
        newGameSettings.vibration = sharedPreferencesRepo.readVibrationState()
    }

}