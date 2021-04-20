package com.example.taboogame.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.taboogame.databinding.FragmentGameConfigurationBinding


class GameConfigurationFragment : Fragment() {

    private lateinit var gameConfigurationViewModel: GameConfigurationViewModel

    private var _binding: FragmentGameConfigurationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameConfigurationViewModel = ViewModelProvider(requireActivity()).get(GameConfigurationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameConfigurationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = this

        binding.startTheGameButton.setOnClickListener {
            addTeamNames()
            Navigation.findNavController(it)
                .navigate(
                    GameConfigurationFragmentDirections.actionGameConfigurationFragmentToGameFragment(
                        gameConfigurationViewModel.newGameSettings
                    )
                )
        }

        gameConfigurationViewModel.setLanguageAndVibration()

        //Set time buttons
        binding.oneMinuteChip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.roundTime = 60000L
        }
        binding.minuteAndHalfChip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.roundTime = 90000L
        }
        binding.twoMinutesChip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.roundTime = 120000L
        }

        //set skip buttons
        binding.skip3Chip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.skipAvailable = 3
        }
        binding.skip5Chip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.skipAvailable = 5
        }
        binding.skip10Chip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.skipAvailable = 10
        }

        //Set points limit buttons
        binding.points20LimitChip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.pointsLimit = 20
        }
        binding.points30LimitChip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.pointsLimit = 30
        }
        binding.points50LimitChip.setOnClickListener {
            gameConfigurationViewModel.newGameSettings.pointsLimit = 50
        }

        return binding.root
    }

    private fun addTeamNames() {
        gameConfigurationViewModel.newGameSettings.teamOneName = binding.submitTeamOneName.text.toString()
        gameConfigurationViewModel.newGameSettings.teamTwoName = binding.submitTeamTwoName.text.toString()
    }
}