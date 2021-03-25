package com.example.taboogame.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.taboogame.R
import com.example.taboogame.databinding.BackButtonPopWindowBinding
import com.example.taboogame.databinding.FragmentGameBinding
import com.example.taboogame.databinding.NextRoundPopupWindowBinding


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var _bindingNextRoundWindow: NextRoundPopupWindowBinding? = null
    private val bindingNextRoundWindow get() = _bindingNextRoundWindow

    private var _bindingBackButtonWindow: BackButtonPopWindowBinding? = null
    private val bindingBackButtonWindow get() = _bindingBackButtonWindow

    private lateinit var mPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )

        mPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val language =
            mPreferences.getString(getString(R.string.key_guessWords_Language_Active), "en")

        val args = GameFragmentArgs.fromBundle(requireArguments())

        binding.teamOneName.text = args.teamOneName
        binding.teamTwoName.text = args.teamTwoName

        viewModelFactory = GameViewModelFactory(
            args.roundTime,
            args.skipAvailable,
            args.pointsLimit,
            args.vibration,
            language!!
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.pauseButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToPauseScreenFragment(
                    args.teamOneName, args.teamTwoName, viewModel.teamOneScore.value ?: 0,
                    viewModel.teamTwoScore.value ?: 0, viewModel.currentTimeString.value ?: "00:00"
                )
            )
            viewModel.timer.cancel()
        }

        viewModel.teamOneUsedAllSkipWords.observe(viewLifecycleOwner, { hasSkipped ->

                binding.skipWordButton.isClickable = !hasSkipped

        })
        viewModel.teamTwoUsedAllSkipWords.observe(viewLifecycleOwner, { hasSkipped ->

                binding.skipWordButton.isClickable = !hasSkipped

        })

        viewModel.nextRoundActive.observe(viewLifecycleOwner, { isActive ->
            if (isActive) {
                nextRoundPopUpWindow()
            }
        })

        viewModel.gameFinished.observe(viewLifecycleOwner, { isFinished ->
            if (isFinished) {
                view?.findNavController()
                    ?.navigate(
                        GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                            args.teamOneName, args.teamTwoName, viewModel.teamOneScore.value ?: 0,
                            viewModel.teamTwoScore.value ?: 0
                        )
                    )
            }
        })

        viewModel.eventBuzz.observe(viewLifecycleOwner, { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })

        val callback = requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                backButtonPopUpWindow()
            }

        return binding.root
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun nextRoundPopUpWindow() {
        _bindingNextRoundWindow =
            NextRoundPopupWindowBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(bindingNextRoundWindow?.root)
            .create()

        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        if (viewModel.teamTwoActive) {
            bindingNextRoundWindow?.activeTeamNamePopUpWindow?.text = binding.teamOneName.text
        } else bindingNextRoundWindow?.activeTeamNamePopUpWindow?.text = binding.teamTwoName.text

        bindingNextRoundWindow?.pointsInActiveRound?.text = viewModel.pointsInActiveRound.toString()

        if (viewModel.teamTwoActive) {
            bindingNextRoundWindow?.nextTeamPopUpWindow?.text = binding.teamTwoName.text
        } else bindingNextRoundWindow?.nextTeamPopUpWindow?.text = binding.teamOneName.text

        binding.pauseButton.performClick()

        bindingNextRoundWindow?.startButtonInPopUpWindow?.setOnClickListener {
            viewModel.restartTimer()
            dialog.dismiss()
            this.findNavController().popBackStack()
        }
    }

    private fun backButtonPopUpWindow() {
        _bindingBackButtonWindow =
            BackButtonPopWindowBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(bindingBackButtonWindow?.root)
            .create()

        dialog?.show()
        dialog?.setCanceledOnTouchOutside(false)

        binding.pauseButton.performClick()

        bindingBackButtonWindow?.endRoundButtonInPopUpWindow?.setOnClickListener {
            this.findNavController()
                .navigate(R.id.action_pauseScreenFragment_to_gameConfigurationFragment)
            dialog.dismiss()
        }
        bindingBackButtonWindow?.resumeRoundButtonInPopUpWindow?.setOnClickListener {
            this.findNavController().popBackStack()
            dialog.dismiss()
        }
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingBackButtonWindow = null
        _binding = null
        _bindingNextRoundWindow = null
    }
}

