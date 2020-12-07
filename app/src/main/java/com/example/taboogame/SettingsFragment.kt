package com.example.taboogame

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taboogame.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.app_language_options_window.*
import kotlinx.android.synthetic.main.guess_words_language_window.*

import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var mPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_settings, container, false)

        mPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        mEditor = mPreferences.edit()

        val prefs = Locale.getDefault().language

        setProperIcons(prefs)

        binding.vibrationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mEditor.putBoolean(getString(R.string.key_isVibration_Active), isChecked)
                mEditor.apply()
            } else {
                mEditor.putBoolean(getString(R.string.key_isVibration_Active), isChecked)
                mEditor.apply()
            }
        }

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mEditor.putBoolean(getString(R.string.key_isNightThemeActive), isChecked)
                mEditor.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                mEditor.putBoolean(getString(R.string.key_isNightThemeActive), isChecked)
                mEditor.apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.appLanguageButton.setOnClickListener {
            appLanguageWindow()
        }

        binding.guessWordsLanguageButton.setOnClickListener {
            guessWordsLanguageWindow()
        }

        binding.vibrationSwitch.isChecked = mPreferences.getBoolean(getString(R.string.key_isVibration_Active), false)
        binding.darkThemeSwitch.isChecked = mPreferences.getBoolean(getString(R.string.key_isNightThemeActive), false)

        return binding.root
    }

    private fun setProperIcons(prefs: String) {
        if (prefs == "en") {
            binding.appLanguageButton.setImageResource(R.drawable.ic_flag_of_uk)
        }
        if (prefs == "pl") {
            binding.appLanguageButton.setImageResource(R.drawable.ic_flag_of_poland_2)
        }
        if (mPreferences.getString(getString(R.string.key_guessWords_Language_Active), "en") == "en") {
            binding.guessWordsLanguageButton.setImageResource(R.drawable.ic_flag_of_uk)
        }
        if (mPreferences.getString(getString(R.string.key_guessWords_Language_Active), "en") == "pl") {
            binding.guessWordsLanguageButton.setImageResource(R.drawable.ic_flag_of_poland_2)
        }
        if (mPreferences.getString(getString(R.string.key_guessWords_Language_Active), "en") == "es") {
            binding.guessWordsLanguageButton.setImageResource(R.drawable.ic_flag_of_spain)
        }
    }

    private fun appLanguageWindow() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val inflater = requireActivity().layoutInflater
        builder?.setView(inflater.inflate(R.layout.app_language_options_window, null))
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()

        dialog?.english_button_appLanguage?.setOnClickListener {
            LocaleManager.setNewLocale(requireContext(), "en")
            activity?.recreate()
            dialog.dismiss()
        }

        dialog?.polish_button_appLanguage?.setOnClickListener {
            LocaleManager.setNewLocale(requireContext(), "pl")
            activity?.recreate()
            dialog.dismiss()
        }

        dialog?.cancel_button_in_appLanguage?.setOnClickListener{
            dialog.dismiss()
        }
    }

    private fun guessWordsLanguageWindow() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val inflater = requireActivity().layoutInflater
        builder?.setView(inflater.inflate(R.layout.guess_words_language_window, null))
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()

        dialog?.english_button_guessWordLanguage?.setOnClickListener {
            mEditor.putString(getString(R.string.key_guessWords_Language_Active), "en")
            mEditor.apply()
            activity?.recreate()
            dialog.dismiss()
        }

        dialog?.polish_button_guessWordLanguage?.setOnClickListener {
            mEditor.putString(getString(R.string.key_guessWords_Language_Active), "pl")
            mEditor.apply()
            activity?.recreate()
            dialog.dismiss()
        }

        dialog?.spanish_button_guessWordLanguage?.setOnClickListener {
            mEditor.putString(getString(R.string.key_guessWords_Language_Active), "es")
            mEditor.apply()
            activity?.recreate()
            dialog.dismiss()
        }

        dialog?.cancel_button_guessWordLanguage?.setOnClickListener {
            dialog.dismiss()
        }
    }
}


