package com.example.taboogame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taboogame.repo.SharedPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferencesRepo: SharedPreferencesRepo,
    application: Application): AndroidViewModel(application) {

//    private val mPreferences =  SharedPreferencesRepo(application.applicationContext)

    fun saveVibrationState(isActive: Boolean) = viewModelScope.launch{
        sharedPreferencesRepo.saveVibrationState(isActive)
    }

    fun saveNightModeState(isActive: Boolean) = viewModelScope.launch{
        sharedPreferencesRepo.saveNightModeState(isActive)
    }

    fun saveGuessWordsLanguageSetting(languageCode: String) = viewModelScope.launch {
        sharedPreferencesRepo.saveGuessWordsLanguageSetting(languageCode)
    }

    fun readVibrationState(): Boolean {
        return sharedPreferencesRepo.readVibrationState()
    }

    fun readNightModeState(): Boolean {
        return sharedPreferencesRepo.readNightModeState()
    }

    fun readGuessWordsLanguageSettings(): String {
        return sharedPreferencesRepo.readGuessWordsLanguageSettings()
    }

}