package com.example.taboogame.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentPauseScreenBinding


class PauseScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPauseScreenBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pause_screen, container, false)

        val args = PauseScreenFragmentArgs.fromBundle(requireArguments())

        setArgumentsFromGameFragment(binding, args)

        binding.playGameImgButton.setOnClickListener{
            this.findNavController().popBackStack()
        }

        binding.rulesButtonOnPauseScreen.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_pauseScreenFragment_to_rulesFragment)
        )

        binding.resumeButton.setOnClickListener{
            this.findNavController().popBackStack()
        }

        binding.endTheGameButton.setOnClickListener{
            this.findNavController()
                    .navigate(PauseScreenFragmentDirections
                    .actionPauseScreenFragmentToGameFinishedFragment(
                            args.teamOneName, args.teamTwoName, args.teamOneScore, args.teamTwoScore))
        }

        return binding.root
    }

    private fun setArgumentsFromGameFragment(binding: FragmentPauseScreenBinding, args: PauseScreenFragmentArgs) {
        binding.timerText.text = args.currentTime
        binding.teamOneScoreText.text = args.teamOneScore.toString()
        binding.teamTwoScoreText.text = args.teamTwoScore.toString()
        binding.teamOneNameInPause.text = args.teamOneName
        binding.teamTwoNameInPause.text = args.teamTwoName
    }

}