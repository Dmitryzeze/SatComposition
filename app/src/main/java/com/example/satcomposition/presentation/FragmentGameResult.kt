package com.example.satcomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.satcomposition.R
import com.example.satcomposition.databinding.FragmentGameResultBinding
import com.example.satcomposition.domain.entity.GameResult

class FragmentGameResult : Fragment() {
    private val result: GameResult by lazy {
        args.result
    }
    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")
    private val args: FragmentGameResultArgs by navArgs()


    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                findNavController().popBackStack()
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}