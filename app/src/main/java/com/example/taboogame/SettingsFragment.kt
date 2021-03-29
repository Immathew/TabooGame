package com.example.taboogame

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taboogame.databinding.AppLanguageOptionsWindowBinding
import com.example.taboogame.databinding.FragmentSettingsBinding
import com.example.taboogame.databinding.GuessWordsLanguageWindowBinding
import com.example.taboogame.utils.LocaleManager
import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var mPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var _bindingGuessWordsLanguage: GuessWordsLanguageWindowBinding? = null
    private val bindingGuessWordsLanguage get() = _bindingGuessWordsLanguage

    private var _bindingAppLanguage: AppLanguageOptionsWindowBinding? = null
    private val bindingAppLanguage get() = _bindingAppLanguage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

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

        binding.vibrationSwitch.isChecked =
            mPreferences.getBoolean(getString(R.string.key_isVibration_Active), false)
        binding.darkThemeSwitch.isChecked =
            mPreferences.getBoolean(getString(R.string.key_isNightThemeActive), false)

        return binding.root
    }

    private fun setProperIcons(prefs: String) {
        when (prefs) {
            "en" -> {
                binding.appLanguageButton.setImageResource(R.drawable.ic_flag_of_uk)
            }
            "pl" -> {
                binding.appLanguageButton.setImageResource(R.drawable.ic_flag_of_poland_2)
            }
        }

        val language = mPreferences.getString(
            getString(R.string.key_guessWords_Language_Active),
            "en"
        )
        when (language) {
            "en" -> {
                binding.guessWordsLanguageButton.setImageResource(R.drawable.ic_flag_of_uk)
            }
            "pl" -> {
                binding.guessWordsLanguageButton.setImageResource(R.drawable.ic_flag_of_poland_2)
            }
            "es" -> {
                binding.guessWordsLanguageButton.setImageResource(R.drawable.ic_flag_of_spain)
            }
        }
    }

    private fun appLanguageWindow() {
        _bindingAppLanguage =
            AppLanguageOptionsWindowBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(bindingAppLanguage?.root)
            .create()
        dialog?.show()

        bindingAppLanguage?.englishButtonAppLanguage?.setOnClickListener {
            LocaleManager.setNewLocale(requireContext(), "en")
            activity?.recreate()
            dialog.dismiss()
        }

        bindingAppLanguage?.polishButtonAppLanguage?.setOnClickListener {
            LocaleManager.setNewLocale(requireContext(), "pl")
            activity?.recreate()
            dialog.dismiss()
        }

        bindingAppLanguage?.cancelButtonInAppLanguage?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun guessWordsLanguageWindow() {
        _bindingGuessWordsLanguage =
            GuessWordsLanguageWindowBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(bindingGuessWordsLanguage?.root)
            .create()
        dialog?.show()

        bindingGuessWordsLanguage?.englishButtonGuessWordLanguage?.setOnClickListener {
            mEditor.putString(getString(R.string.key_guessWords_Language_Active), "en")
            mEditor.apply()
            activity?.recreate()
            dialog?.dismiss()
        }

        bindingGuessWordsLanguage?.polishButtonGuessWordLanguage?.setOnClickListener {
            mEditor.putString(getString(R.string.key_guessWords_Language_Active), "pl")
            mEditor.apply()
            activity?.recreate()
            dialog.dismiss()
        }

        bindingGuessWordsLanguage?.spanishButtonGuessWordLanguage?.setOnClickListener {
            mEditor.putString(getString(R.string.key_guessWords_Language_Active), "es")
            mEditor.apply()
            activity?.recreate()
            dialog.dismiss()
        }

        bindingGuessWordsLanguage?.cancelButtonGuessWordLanguage?.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _bindingAppLanguage = null
        _bindingGuessWordsLanguage = null
    }
}


