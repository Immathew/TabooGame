package com.example.taboogame

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.taboogame.databinding.AppLanguageOptionsWindowBinding
import com.example.taboogame.databinding.FragmentSettingsBinding
import com.example.taboogame.databinding.GuessWordsLanguageWindowBinding
import com.example.taboogame.utils.LocaleManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var _bindingGuessWordsLanguage: GuessWordsLanguageWindowBinding? = null
    private val bindingGuessWordsLanguage get() = _bindingGuessWordsLanguage

    private var _bindingAppLanguage: AppLanguageOptionsWindowBinding? = null
    private val bindingAppLanguage get() = _bindingAppLanguage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        setProperAppLanguageIcons(Locale.getDefault().language)
        setProperGuessWordsLanguageIcons()

        binding.vibrationSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveVibrationState(isChecked)
        }

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                settingsViewModel.saveNightModeState(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                settingsViewModel.saveNightModeState(isChecked)
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
            settingsViewModel.readVibrationState()

        binding.darkThemeSwitch.isChecked =
            settingsViewModel.readNightModeState()

        return binding.root
    }

    private fun setProperAppLanguageIcons(languageCode: String) {
        when (languageCode) {
            "en" -> {
                binding.appLanguageButton.setImageResource(R.drawable.ic_flag_of_uk)
            }
            "pl" -> {
                binding.appLanguageButton.setImageResource(R.drawable.ic_flag_of_poland_2)
            }
        }
    }

    private fun setProperGuessWordsLanguageIcons() {
        when (settingsViewModel.readGuessWordsLanguageSettings()) {
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
            settingsViewModel.saveGuessWordsLanguageSetting("en")
            setProperGuessWordsLanguageIcons()
            dialog?.dismiss()
        }

        bindingGuessWordsLanguage?.polishButtonGuessWordLanguage?.setOnClickListener {
            settingsViewModel.saveGuessWordsLanguageSetting("pl")
            setProperGuessWordsLanguageIcons()
            dialog.dismiss()
        }

        bindingGuessWordsLanguage?.spanishButtonGuessWordLanguage?.setOnClickListener {
            settingsViewModel.saveGuessWordsLanguageSetting("es")
            setProperGuessWordsLanguageIcons()
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


