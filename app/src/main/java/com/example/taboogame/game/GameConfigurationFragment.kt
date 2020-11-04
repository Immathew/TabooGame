package com.example.taboogame.game

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameBinding
import com.example.taboogame.databinding.FragmentGameConfigurationBinding
import kotlinx.android.synthetic.main.fragment_game_configuration.*
import kotlinx.android.synthetic.main.fragment_game_configuration.view.*


class GameConfigurationFragment : Fragment() {



    private var roundTime = 60000L
    private var skipAvailable = 3
    private var pointsLimit = 20
    private var teamOneName ="Team 1"
    private var teamTwoName="Team 2"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameConfigurationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_configuration, container, false)

        binding.startTheGameButton.setOnClickListener{view: View ->
            Navigation.findNavController(view).navigate(R.id.action_gameConfigurationFragment_to_gameFragment)
            addTeamNames()
        }

        //Set time buttons
        binding.oneMinuteButton.setOnClickListener {
            roundTime = 60000L
            resetTimeButtons()
            one_minute_button.setTextColor(Color.RED)
        }
        binding.minuteAndAHalfButton.setOnClickListener {
            roundTime = 90000L
            resetTimeButtons()
            minute_and_a_half_button.setTextColor(Color.RED)
        }
        binding.twoMinutesButton.setOnClickListener {
            roundTime = 120000L
            resetTimeButtons()
            two_minutes_button.setTextColor(Color.RED)
        }

        //set skip buttons
        binding.skip3Button.setOnClickListener{
            skipAvailable = 3
            resetSkipButtons()
            skip_3_button.setTextColor(Color.RED)
        }
        binding.skip5Button.setOnClickListener{
            skipAvailable = 5
            resetSkipButtons()
            skip_5_button.setTextColor(Color.RED)
        }
        binding.skip10Button.setOnClickListener{
            skipAvailable = 10
            resetSkipButtons()
            skip_10_button.setTextColor(Color.RED)
        }

        //Set points limit buttons
        binding.points20Button.setOnClickListener{
            pointsLimit = 20
            resetPointsLimitButton()
            points_20_button.setTextColor(Color.RED)
        }
        binding.points30Button.setOnClickListener{
            pointsLimit = 30
            resetPointsLimitButton()
            points_30_button.setTextColor(Color.RED)
        }
        binding.points50Button.setOnClickListener{
            pointsLimit = 50
            resetPointsLimitButton()
            points_50_button.setTextColor(Color.RED)
        }

        return binding.root
    }

    private fun resetTimeButtons() {
        one_minute_button.setTextColor(Color.BLACK)
        minute_and_a_half_button.setTextColor(Color.BLACK)
        two_minutes_button.setTextColor(Color.BLACK)
    }

    private fun resetSkipButtons() {
        skip_3_button.setTextColor(Color.BLACK)
        skip_5_button.setTextColor(Color.BLACK)
        skip_10_button.setTextColor(Color.BLACK)
    }

    private fun resetPointsLimitButton() {
        points_20_button.setTextColor(Color.BLACK)
        points_30_button.setTextColor(Color.BLACK)
        points_50_button.setTextColor(Color.BLACK)
    }

    private fun addTeamNames() {
        teamOneName = submit_team_one_name.text.toString()
        teamTwoName = submit_team_two_name.text.toString()
    }
}