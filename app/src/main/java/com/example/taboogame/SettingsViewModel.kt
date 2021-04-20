package com.example.taboogame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taboogame.repo.SharedPreferencesRepo
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val mPreferences =  SharedPreferencesRepo(application.applicationContext)

    fun saveVibrationState(isActive: Boolean) = viewModelScope.launch{
        mPreferences.saveVibrationState(isActive)
    }

    fun saveNightModeState(isActive: Boolean) = viewModelScope.launch{
        mPreferences.saveNightModeState(isActive)
    }

    fun saveGuessWordsLanguageSetting(languageCode: String) = viewModelScope.launch {
        mPreferences.saveGuessWordsLanguageSetting(languageCode)
    }

    fun readVibrationState(): Boolean {
        return mPreferences.readVibrationState()
    }

    fun readNightModeState(): Boolean {
        return mPreferences.readNightModeState()
    }

    fun readGuessWordsLanguageSettings(): String {
        return mPreferences.readGuessWordsLanguageSettings()
    }

}