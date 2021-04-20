package com.example.taboogame.repo

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesRepo(context: Context) {

    private var mPreferences: SharedPreferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    private var mEditor: SharedPreferences.Editor = mPreferences.edit()

    fun saveVibrationState(isActive: Boolean) {
        mEditor.putBoolean(
            "Is_vibration_active",
            isActive
        )
        mEditor.apply()
    }

    fun readVibrationState(): Boolean {
        return mPreferences.getBoolean(
            "Is_vibration_active",
            false
        )
    }

    fun saveNightModeState(isActive: Boolean) {
        mEditor.putBoolean(
            "Is_night_theme_active",
            isActive
        )
        mEditor.apply()
    }

    fun readNightModeState(): Boolean {
        return mPreferences.getBoolean(
            "Is_night_theme_active",
            false
        )
    }

    fun saveGuessWordsLanguageSetting(languageCode: String) {
        mEditor.putString(
            "GuessWords_active_language",
            languageCode
        )
        mEditor.apply()
    }

    fun readGuessWordsLanguageSettings(): String {
        return mPreferences.getString("GuessWords_active_language", "en")!!
    }

}