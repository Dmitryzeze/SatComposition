package com.example.satcomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.satcomposition.R
import com.example.satcomposition.databinding.FragmentGameResultBinding
import com.example.satcomposition.domain.entity.GameResult
import com.example.satcomposition.domain.entity.Level

class FragmentGameResult : Fragment() {
    private lateinit var result: GameResult
    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()


    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                result.gameSettings.minCountOfRightAnswer
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                result.countOfRightAnswer
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                result.gameSettings.minPercentOfRightAnswer
            )
            buttonRetry.setOnClickListener {
                launchChooseLevelFragment()
            }
        }
    }

    private fun getSmileResId(): Int {
        return if (result.winner) {
            R.drawable.ic_smile_win
        } else {
            R.drawable.ic_smile_lose
        }
    }

    private fun getPercentOfRightAnswers() = with(result) {
        if (countOfQuestions == 0) {
            0
        } else {
            (countOfRightAnswer.toDouble() / countOfQuestions.toDouble() * 100).toInt()
        }
    }

    private fun setupClickListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchChooseLevelFragment()
                }
            })

    }

    private fun launchChooseLevelFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentGame.newInstance(Level.EASY))
            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let {
            result = it
        }
    }

    companion object {
        private const val GAME_RESULT = "result"
        fun newInstance(result: GameResult): FragmentGameResult {
            return FragmentGameResult().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, result)
                }
            }
        }
    }
}