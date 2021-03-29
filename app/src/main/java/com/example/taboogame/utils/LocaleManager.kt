package com.example.taboogame.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LocaleManager {

    fun setLocale(mContext: Context): Context {
        return if (LanguageHelper.instance!!.getLanguagePref() != null)
            updateResources(mContext, LanguageHelper.instance!!.getLanguagePref()!!)
        else
            mContext
    }

    fun setNewLocale(mContext: Context, language: String): Context {
        LanguageHelper.instance!!setLanguagePref(language)
        return updateResources(mContext, language)
    }

    private fun updateResources(context: Context, language: String): Context {
        var localContext= context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        localContext = context.createConfigurationContext(config)
        return localContext
    }
}