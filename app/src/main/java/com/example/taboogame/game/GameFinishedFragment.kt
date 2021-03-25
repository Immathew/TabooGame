package com.example.taboogame.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentGameFinishedBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_finished, container, false)

        val args = GameFinishedFragmentArgs.fromBundle(requireArguments())

        binding.teamOneName.text = args.teamOneName
        binding.teamTwoName.text = args.teamTwoName
        binding.teamOneScore.text = args.teamOneScore.toString()
        binding.teamTwoScore.text = args.teamTwoScore.toString()

        binding.playAgainButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_gameFinishedFragment_to_gameConfigurationFragment)
        )

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view?.findNavController()?.navigate(R.id.action_gameFinishedFragment_to_gameConfigurationFragment)
        }
        return binding.root
    }

}