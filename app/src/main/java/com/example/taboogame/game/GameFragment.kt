package com.example.taboogame.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.taboogame.PauseScreenFragmentDirections
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.back_button_pop_window.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_pause_screen.*
import kotlinx.android.synthetic.main.next_round_popup_window.*
import kotlin.concurrent.timer


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false)

        val args = GameFragmentArgs.fromBundle(requireArguments())

        binding.teamOneName.text = args.teamOneName
        binding.teamTwoName.text = args.teamTwoName

        viewModelFactory = GameViewModelFactory(args.roundTime, args.skipAvailable, args.pointsLimit)
        viewModel = ViewModelProvider(this, viewModelFactory)
                    .get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.pauseButton.setOnClickListener { view: View ->
            view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToPauseScreenFragment(
                    args.teamOneName, args.teamTwoName, viewModel.teamOneScore.value ?: 0,
                    viewModel.teamTwoScore.value ?: 0, viewModel.currentTimeString.value ?: "00:00"
            ))
        }

        viewModel.teamOneUsedAllSkipWords.observe(viewLifecycleOwner, { hasSkipped ->
            skip_word_button.isClickable = !hasSkipped
        })
        viewModel.teamTwoUsedAllSkipWords.observe(viewLifecycleOwner, { hasSkipped ->
            skip_word_button.isClickable = !hasSkipped
        })

        viewModel.nextRoundActive.observe(viewLifecycleOwner, { isActive ->
            if (isActive) {
                viewModel.timer.cancel()
                nextRoundPopUpWindow()
            }
        })

        viewModel.gameFinished.observe(viewLifecycleOwner, { isFinished ->
            if (isFinished) {
                view?.findNavController()
                ?.navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                        args.teamOneName, args.teamTwoName, viewModel.teamOneScore.value ?: 0,
                        viewModel.teamTwoScore.value ?: 0 ))
            }
        })

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backButtonPopUpWindow()
        }

        return binding.root
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun nextRoundPopUpWindow() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val inflater = requireActivity().layoutInflater
            builder?.setView(inflater.inflate(R.layout.next_round_popup_window, null))
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
        dialog?.setCanceledOnTouchOutside(false)

        dialog?.points_in_round_popUp_window?.text = "Twoja drużyna zdobyła "
        dialog?.start_button_in_popUp_window?.setOnClickListener {
            viewModel.restartTimer()
            dialog.dismiss()
        }
    }

    private fun backButtonPopUpWindow() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val inflater = requireActivity().layoutInflater
        builder?.setView(inflater.inflate(R.layout.back_button_pop_window, null))
        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
        dialog?.setCanceledOnTouchOutside(false)
        pause_button.performClick()

        dialog?.end_round_button_in_popUp_window?.setOnClickListener {
           this.findNavController().navigate(R.id.action_pauseScreenFragment_to_gameConfigurationFragment)
            dialog.dismiss()
        }
        dialog?.resume_round_button_in_popUp_window?.setOnClickListener {
            this.findNavController().popBackStack()
            dialog.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.timer.cancel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.timer.start()
    }
}

