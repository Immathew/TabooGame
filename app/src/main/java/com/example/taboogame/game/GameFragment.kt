package com.example.taboogame.game

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taboogame.R
import com.example.taboogame.databinding.BackButtonPopWindowBinding
import com.example.taboogame.databinding.FragmentGameBinding
import com.example.taboogame.databinding.NextRoundPopupWindowBinding
import com.example.taboogame.utils.OnSwipeTouchListener


class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()
    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var _bindingNextRoundWindow: NextRoundPopupWindowBinding? = null
    private val bindingNextRoundWindow get() = _bindingNextRoundWindow

    private var _bindingBackButtonWindow: BackButtonPopWindowBinding? = null
    private val bindingBackButtonWindow get() = _bindingBackButtonWindow

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )

        binding.teamOneName.text = args.newGameSettings.teamOneName
        binding.teamTwoName.text = args.newGameSettings.teamTwoName

        viewModelFactory = GameViewModelFactory(
            args.newGameSettings
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.pauseButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToPauseScreenFragment(
                    args.newGameSettings.teamOneName,
                    args.newGameSettings.teamTwoName,
                    viewModel.teamOneScore.value ?: 0,
                    viewModel.teamTwoScore.value ?: 0,
                    viewModel.currentTimeString.value ?: "00:00"
                )
            )
            viewModel.timer.cancel()
        }

        viewModel.teamOneWordsSkipped.observe(viewLifecycleOwner, { value ->
            if (viewModel.teamOneActive && value != 0) {
                binding.skipWordButton.isClickable = true
            } else binding.skipWordButton.isClickable =
                viewModel.teamTwoActive && viewModel.teamTwoWordsSkipped.value != 0

        })
        viewModel.teamTwoWordsSkipped.observe(viewLifecycleOwner, { value ->
            if (viewModel.teamTwoActive && value != 0) {
                binding.skipWordButton.isClickable = true
            } else binding.skipWordButton.isClickable =
                viewModel.teamOneActive && viewModel.teamOneWordsSkipped.value != 0

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
                            args.newGameSettings.teamOneName,
                            args.newGameSettings.teamTwoName,
                            viewModel.teamOneScore.value ?: 0,
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

        val scale = requireActivity().resources.displayMetrics.density
        binding.wordsToGuessCardView.cameraDistance = (3000F * scale)

        val goodAnswerAnimation: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.wordsToGuessCardView, "rotationY", 360f)
        val goodAnswerAnimationColor: ObjectAnimator = ObjectAnimator.ofArgb(
            binding.wordsToGuessCardView,
            "strokeColor",
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.white)
        )

        binding.nextWordButton.setOnClickListener {
            AnimatorSet().apply {
                playTogether(
                    goodAnswerAnimation,
                    goodAnswerAnimationColor
                )
                duration = 700
                start()
            }
            viewModel.nextWord()
        }

        val badAnswerAnimation: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.wordsToGuessCardView, "rotationY", 360f, 0f)
        val badAnswerAnimationColor: ObjectAnimator = ObjectAnimator.ofArgb(
            binding.wordsToGuessCardView,
            "strokeColor",
            ContextCompat.getColor(requireContext(), R.color.red),
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        binding.skipWordMinusPointButton.setOnClickListener {
            AnimatorSet().apply {
                playTogether(
                    badAnswerAnimation,
                    badAnswerAnimationColor
                )
                duration = 700
                start()
            }
            viewModel.skipWordAndLosePoint()
        }

        val skipAnswerAnimation: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.wordsToGuessCardView, "rotationX", 360f, 0f)
        val skipAnswerAnimationColor: ObjectAnimator = ObjectAnimator.ofArgb(
            binding.wordsToGuessCardView,
            "strokeColor",
            ContextCompat.getColor(requireContext(), R.color.yellow),
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        binding.skipWordButton.setOnClickListener {
            AnimatorSet().apply {
                playTogether(
                    skipAnswerAnimation,
                    skipAnswerAnimationColor
                )
                duration = 700
                start()
            }
            viewModel.skipWord()
        }

        binding.wordsToGuessCardView.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                AnimatorSet().apply {
                    playTogether(
                        goodAnswerAnimation,
                        goodAnswerAnimationColor
                    )
                    duration = 700
                    start()
                }
                viewModel.nextWord()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                AnimatorSet().apply {
                    playTogether(
                        badAnswerAnimation,
                        badAnswerAnimationColor
                    )
                    duration = 700
                    start()
                }
                viewModel.skipWordAndLosePoint()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                AnimatorSet().apply {
                    playTogether(
                        skipAnswerAnimation,
                        skipAnswerAnimationColor
                    )
                    duration = 700
                    start()
                }
                viewModel.skipWord()
            }
        })

        return binding.root
    }

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
        if (viewModel.teamOneActive) {
            binding.activeTeamNameTextView.text = args.newGameSettings.teamOneName
        } else {
            binding.activeTeamNameTextView.text = args.newGameSettings.teamTwoName
        }
        viewModel.timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingBackButtonWindow = null
        _binding = null
        _bindingNextRoundWindow = null
    }
}

