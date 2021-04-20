package com.example.taboogame.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.taboogame.models.NewGameSettings
import com.example.taboogame.repo.SharedPreferencesRepo

class GameConfigurationViewModel(application: Application) : AndroidViewModel(application) {

    private var mPreferences = SharedPreferencesRepo(application.applicationContext)

    var newGameSettings = NewGameSettings(
        60000L, 3, 20, "Team 1", "Team 2", false, ""
    )

    fun setLanguageAndVibration() {
        newGameSettings.language = mPreferences.readGuessWordsLanguageSettings()
        newGameSettings.vibration = mPreferences.readVibrationState()
    }

}