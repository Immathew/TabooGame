package com.example.taboogame.utils

import android.content.Context
import android.content.res.Configuration
import com.example.taboogame.MyApplicationWithLanguageHelper
import java.util.*

object LocaleManager {

    fun setLocale(mContext: Context): Context {
        return if (MyApplicationWithLanguageHelper.instance!!.getLanguagePref() != null)
            updateResources(
                mContext,
                MyApplicationWithLanguageHelper.instance!!.getLanguagePref()!!
            )
        else
            mContext
    }

    fun setNewLocale(mContext: Context, language: String): Context {
        MyApplicationWithLanguageHelper.instance!! setLanguagePref (language)
        return updateResources(mContext, language)
    }

    private fun updateResources(context: Context, language: String): Context {
        var localContext = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        localContext = context.createConfigurationContext(config)
        return localContext
    }
}