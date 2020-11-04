package com.example.taboogame.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameFinishedBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_finished, container, false)

        binding.playAgainButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_gameFinishedFragment_to_gameConfigurationFragment)
        )

        return binding.root
    }

}