package com.example.satcomposition.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.satcomposition.R
import com.example.satcomposition.databinding.FragmentGameResultBinding
import com.example.satcomposition.domain.entity.GameResult

class FragmentGameResult :Fragment() {
    private lateinit var result : GameResult
    private var _binding : FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        Log.d("Arguments",result.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentGameResultBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            launchChooseLevelFragment()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
//                retryGame()
                launchChooseLevelFragment()
            }
        })
    }
    private fun launchChooseLevelFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(FragmentChooseLevel.NAME)
            .replace(R.id.main_container, FragmentChooseLevel.newInstance())
            .commit()
    }
    private fun retryGame(){
        requireActivity().supportFragmentManager.popBackStack(FragmentChooseLevel.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun parseArgs() {
         requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let{
             result = it
        }
    }
    companion object {
        private const val GAME_RESULT = "result"
        fun newInstance(result: GameResult): FragmentGameResult{
            return FragmentGameResult().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, result)
                }
            }
        }
    }
}