package com.example.satcomposition.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.satcomposition.R
import com.example.satcomposition.data.GameRepositoryImpl
import com.example.satcomposition.databinding.FragmentGameBinding
import com.example.satcomposition.domain.entity.GameResult
import com.example.satcomposition.domain.entity.Level


class FragmentGame: Fragment() {
    private lateinit var level : Level
    private var _binding : FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        Log.d("Arguments",level.toString())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = GameRepositoryImpl
        binding.tvOption1.setOnClickListener {
            launchFragmentGameResult(GameResult(
                true,
                5,
                6,
                repository.getGameSettings(level)
            ))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun launchFragmentGameResult(result: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentGameResult.newInstance(result))
            .commit()
    }
    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object {
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): FragmentGame{
            return FragmentGame().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}