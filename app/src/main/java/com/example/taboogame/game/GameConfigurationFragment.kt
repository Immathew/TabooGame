package com.example.taboogame.game


import android.annotation.SuppressLint
import android.content.SharedPreferences

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameConfigurationBinding
import kotlinx.android.synthetic.main.fragment_game_configuration.*


class GameConfigurationFragment : Fragment() {

    private lateinit var mPreferences: SharedPreferences
    private var vibration = true

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
        val binding: FragmentGameConfigurationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_configuration, container, false)

        mPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        vibration = mPreferences.getBoolean(getString(R.string.key_isVibration_Active), false)

        setStartingConfiguration()

        binding.startTheGameButton.setOnClickListener{view: View ->
            addTeamNames()
            Navigation.findNavController(view)
                .navigate(GameConfigurationFragmentDirections.actionGameConfigurationFragmentToGameFragment(
                    roundTime, skipAvailable, pointsLimit, teamOneName, teamTwoName, vibration))
        }

        //Set time buttons
        binding.oneMinuteButton.setOnClickListener {
            roundTime = 60000L
            resetTimeButtons()
            one_minute_button.setTextColor(android.graphics.Color.WHITE)
            one_minute_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

        }
        binding.minuteAndAHalfButton.setOnClickListener {
            roundTime = 90000L
            resetTimeButtons()
            minute_and_a_half_button.setTextColor(android.graphics.Color.WHITE)
            minute_and_a_half_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

        }
        binding.twoMinutesButton.setOnClickListener {
            roundTime = 120000L
            resetTimeButtons()
            two_minutes_button.setTextColor(android.graphics.Color.WHITE)
            two_minutes_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }

        //set skip buttons
        binding.skip3Button.setOnClickListener{
            skipAvailable = 3
            resetSkipButtons()
            skip_3_button.setTextColor(android.graphics.Color.WHITE)
            skip_3_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))

        }
        binding.skip5Button.setOnClickListener{
            skipAvailable = 5
            resetSkipButtons()
            skip_5_button.setTextColor(android.graphics.Color.WHITE)
            skip_5_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
        binding.skip10Button.setOnClickListener{
            skipAvailable = 10
            resetSkipButtons()
            skip_10_button.setTextColor(android.graphics.Color.WHITE)
            skip_10_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }

        //Set points limit buttons
        binding.points20Button.setOnClickListener{
            pointsLimit = 20
            resetPointsLimitButton()
            points_20_button.setTextColor(android.graphics.Color.WHITE)
            points_20_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
        binding.points30Button.setOnClickListener{
            pointsLimit = 30
            resetPointsLimitButton()
            points_30_button.setTextColor(android.graphics.Color.WHITE)
            points_30_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
        binding.points50Button.setOnClickListener{
            pointsLimit = 50
            resetPointsLimitButton()
            points_50_button.setTextColor(android.graphics.Color.WHITE)
            points_50_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }

        return binding.root
    }

    private fun setStartingConfiguration() {
        roundTime = 60000L
        skipAvailable = 3
        pointsLimit = 20
    }

    private fun resetTimeButtons() {
        one_minute_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        minute_and_a_half_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        two_minutes_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun resetSkipButtons() {
        skip_3_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        skip_5_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        skip_10_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun resetPointsLimitButton() {
        points_20_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        points_30_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        points_50_button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun addTeamNames() {
        teamOneName = submit_team_one_name.text.toString()
        teamTwoName = submit_team_two_name.text.toString()
    }
}