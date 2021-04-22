package com.example.taboogame

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**class for changing language in application
 *
 * Annotated with Hilt for dependencies*/

@HiltAndroidApp
class MyApplicationWithLanguageHelper: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    infix fun setLanguagePref(localeKey: String) {
        val pref = getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
        pref.putString(LOCALE, localeKey)
        pref.apply()
    }

    fun getLanguagePref(): String? {
        val pref = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return pref.getString(LOCALE, "en")
    }

    companion object {
        var instance: MyApplicationWithLanguageHelper? = null
        const val PREFS: String = "SHARED_PREFS"
        const val LOCALE: String = "LOCALE"
    }
}