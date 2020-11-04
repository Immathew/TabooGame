package com.example.taboogame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.taboogame.databinding.FragmentPauseScreenBinding
import kotlinx.coroutines.flow.callbackFlow


class PauseScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPauseScreenBinding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_pause_screen, container, false)

        binding.playGameImgButton.setOnClickListener{
            this.findNavController().popBackStack()
        }

        binding.rulesButtonOnPauseScreen.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_pauseScreenFragment_to_rulesFragment)
        )

        binding.resumeButton.setOnClickListener{
            this.findNavController().popBackStack()
        }

        binding.endTheGameButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_pauseScreenFragment_to_gameFinishedFragment)
        )

        return binding.root
    }

}