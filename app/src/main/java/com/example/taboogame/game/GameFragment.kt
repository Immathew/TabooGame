package com.example.taboogame.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.taboogame.R
import com.example.taboogame.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.next_round_popup_window.*


class GameFragment : Fragment() {


    // TODO DATA TRANSFER FROM GAME CONFIGURATION

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.timer.start()

        binding.pauseButton.setOnClickListener{ view: View ->
            viewModel.timer.cancel()
            view.findNavController().navigate(R.id.action_gameFragment_to_pauseScreenFragment)
        }

        viewModel.teamOneUsedAllSkipWords.observe(viewLifecycleOwner, { hasSkipped ->
            skip_word_button.isClickable = !hasSkipped
        })
        viewModel.teamTwoUsedAllSkipWords.observe(viewLifecycleOwner, { hasSkipped ->
            skip_word_button.isClickable = !hasSkipped
        })

        viewModel.nextRoundActive.observe(viewLifecycleOwner,{isActive ->
            if (isActive) {
                viewModel.timer.cancel()
                popUpWindow()
            }
        })

        return binding.root
    }

    @SuppressLint("InflateParams")
    private fun popUpWindow(){
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val inflater = requireActivity().layoutInflater
            builder?.setView(inflater.inflate(R.layout.next_round_popup_window, null))
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
        dialog?.setCanceledOnTouchOutside(false)

        dialog?.start_button_in_popUp_window?.setOnClickListener {
            viewModel.restartTimer()
            dialog.dismiss()
        }
    }



}

