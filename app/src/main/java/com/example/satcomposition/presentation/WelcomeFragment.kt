package com.example.satcomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.satcomposition.R
import com.example.satcomposition.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding : FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener {
            launchChooseLevelFragment()
        }

    }
    private fun launchChooseLevelFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(FragmentChooseLevel.NAME)
            .replace(R.id.main_container, FragmentChooseLevel.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}