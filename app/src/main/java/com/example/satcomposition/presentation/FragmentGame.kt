package com.example.satcomposition.presentation

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.satcomposition.R
import com.example.satcomposition.databinding.FragmentGameBinding
import com.example.satcomposition.domain.entity.GameResult
import com.example.satcomposition.domain.entity.Level


class FragmentGame : Fragment() {
    private lateinit var level: Level
    private val viewModelFactory by lazy {
        GameViewModelFactory(
        level,
        requireActivity().application)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[GameViewModel::class.java]
    }
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        Log.d("Arguments", level.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenerToOption()

    }
private fun setClickListenerToOption(){
    for (tvOption in tvOptions){
        tvOption.setOnClickListener {
            viewModel.chooseAnswer(tvOption.text.toString().toInt())
        }
    }
}

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()

            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }

        }
        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCountOfRightAnswer.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.tvAnswersProgress.setTextColor(color)
        }
        viewModel.enoughPercentOfRightAnswer.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList  = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchFragmentGameResult(it)
        }
        viewModel.progressAnswer.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun launchFragmentGameResult(result: GameResult) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentGameResult.newInstance(result))
            .commit()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): FragmentGame {
            return FragmentGame().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}
