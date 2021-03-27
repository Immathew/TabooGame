package com.example.taboogame.game



import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameConfigurationBinding
import com.example.taboogame.models.NewGameSettings


class GameConfigurationFragment : Fragment() {

    private lateinit var mPreferences: SharedPreferences

    private var _binding: FragmentGameConfigurationBinding? = null
    private val binding get() = _binding!!

    private var newGameSettings = NewGameSettings(
        6000L, 3, 20, "Team 1", "Team 2", false, "en"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameConfigurationBinding.inflate(layoutInflater, container, false)

        mPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val setVibration =
            mPreferences.getBoolean(getString(R.string.key_isVibration_Active), false)

        mPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val setLanguage =
            mPreferences.getString(getString(R.string.key_guessWords_Language_Active), "en")
                .toString()

        newGameSettings.vibration = setVibration
        newGameSettings.language = setLanguage


        binding.startTheGameButton.setOnClickListener {
            addTeamNames()
            Navigation.findNavController(it)
                .navigate(
                    GameConfigurationFragmentDirections.actionGameConfigurationFragmentToGameFragment(
                        newGameSettings
                    )
                )
        }

        //Set time buttons
        binding.oneMinuteChip.setOnClickListener {
            newGameSettings.roundTime = 10000L
        }
        binding.minuteAndHalfChip.setOnClickListener {
            newGameSettings.roundTime = 90000L
        }
        binding.twoMinutesChip.setOnClickListener {
            newGameSettings.roundTime = 120000L
        }

        //set skip buttons
        binding.skip3Chip.setOnClickListener {
            newGameSettings.skipAvailable = 3
        }
        binding.skip5Chip.setOnClickListener {
            newGameSettings.skipAvailable = 5
        }
        binding.skip10Chip.setOnClickListener {
            newGameSettings.skipAvailable = 10
        }

        //Set points limit buttons
        binding.points20LimitChip.setOnClickListener {
            newGameSettings.pointsLimit = 20
        }
        binding.points30LimitChip.setOnClickListener {
            newGameSettings.pointsLimit = 30
        }
        binding.points50LimitChip.setOnClickListener {
            newGameSettings.pointsLimit = 50
        }

        return binding.root
    }

    private fun addTeamNames() {
        newGameSettings.teamOneName = binding.submitTeamOneName.text.toString()
        newGameSettings.teamTwoName = binding.submitTeamTwoName.text.toString()
    }
}