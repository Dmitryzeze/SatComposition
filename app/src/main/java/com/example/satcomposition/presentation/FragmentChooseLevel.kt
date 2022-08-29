package com.example.satcomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.satcomposition.R
import com.example.satcomposition.databinding.FragmentChooseLevelBinding
import com.example.satcomposition.domain.entity.Level

class FragmentChooseLevel : Fragment() {
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchFragmentGame(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchFragmentGame(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchFragmentGame(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchFragmentGame(Level.HARD)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchFragmentGame(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_container, FragmentGame.newInstance(level))
            .commit()
    }

    companion object {
        const val NAME = "FragmentChooseLevel"
        fun newInstance(): FragmentChooseLevel {
            return FragmentChooseLevel()
        }
    }
}