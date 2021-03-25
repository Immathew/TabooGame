package com.example.taboogame.game


import android.annotation.SuppressLint
import android.content.SharedPreferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameConfigurationBinding
import com.example.taboogame.models.NewGameSettings


class GameConfigurationFragment : Fragment() {

    private lateinit var mPreferences: SharedPreferences
    private var vibration = true

    private var _binding: FragmentGameConfigurationBinding? = null
    private val binding get() = _binding!!

    private var newGameSettings = NewGameSettings(
        6000L, 3, 20, "Team 1", "Team 2"
    )

    private var roundTime = 60000L
    private var skipAvailable = 3
    private var pointsLimit = 20
    private var teamOneName = "Team 1"
    private var teamTwoName = "Team 2"

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameConfigurationBinding.inflate(layoutInflater, container, false)

        mPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        vibration = mPreferences.getBoolean(getString(R.string.key_isVibration_Active), false)

        setStartingConfiguration()

        binding.startTheGameButton.setOnClickListener {
            addTeamNames()
            Navigation.findNavController(it)
                .navigate(
                    GameConfigurationFragmentDirections.actionGameConfigurationFragmentToGameFragment(
                        roundTime, skipAvailable, pointsLimit, teamOneName, teamTwoName, vibration
                    )
                )
        }

        //Set time buttons
        binding.oneMinuteChip.setOnClickListener {
            roundTime = 10000L
            resetTimeButtons()
            binding.oneMinuteChip.setTextColor(android.graphics.Color.WHITE)
            binding.oneMinuteChip.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )

        }
        binding.minuteAndHalfChip.setOnClickListener {
            roundTime = 90000L
            resetTimeButtons()
            binding.minuteAndHalfChip.setTextColor(android.graphics.Color.WHITE)
            binding.minuteAndHalfChip.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )

        }
        binding.twoMinutesChip.setOnClickListener {
            roundTime = 120000L
            resetTimeButtons()
            binding.twoMinutesChip.setTextColor(android.graphics.Color.WHITE)
            binding.twoMinutesChip.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }

        //set skip buttons
        binding.skip3Button.setOnClickListener {
            skipAvailable = 3
            resetSkipButtons()
            binding.skip3Button.setTextColor(android.graphics.Color.WHITE)
            binding.skip3Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

        }
        binding.skip5Button.setOnClickListener {
            skipAvailable = 5
            resetSkipButtons()
            binding.skip5Button.setTextColor(android.graphics.Color.WHITE)
            binding.skip5Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
        binding.skip10Button.setOnClickListener {
            skipAvailable = 10
            resetSkipButtons()
            binding.skip10Button.setTextColor(android.graphics.Color.WHITE)
            binding.skip10Button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }

        //Set points limit buttons
        binding.points20Button.setOnClickListener {
            pointsLimit = 20
            resetPointsLimitButton()
            binding.points20Button.setTextColor(android.graphics.Color.WHITE)
            binding.points20Button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }
        binding.points30Button.setOnClickListener {
            pointsLimit = 30
            resetPointsLimitButton()
            binding.points30Button.setTextColor(android.graphics.Color.WHITE)
            binding.points30Button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }
        binding.points50Button.setOnClickListener {
            pointsLimit = 50
            resetPointsLimitButton()
            binding.points50Button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.points50Button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }

        return binding.root
    }

    private fun setStartingConfiguration() {
        roundTime = 60000L
        skipAvailable = 3
        pointsLimit = 20
    }

    private fun resetTimeButtons() {
        binding.oneMinuteChip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        binding.minuteAndHalfChip.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.blue
            )
        )
        binding.twoMinutesChip.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.blue
            )
        )
    }

    private fun resetSkipButtons() {
        binding.skip3Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        binding.skip5Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        binding.skip10Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun resetPointsLimitButton() {
        binding.points20Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        binding.points30Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        binding.points50Button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun addTeamNames() {
        teamOneName = binding.submitTeamOneName.text.toString()
        teamTwoName = binding.submitTeamTwoName.text.toString()
    }
}