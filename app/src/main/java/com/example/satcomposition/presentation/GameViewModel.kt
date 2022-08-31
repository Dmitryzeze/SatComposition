package com.example.satcomposition.presentation

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.satcomposition.data.GameRepositoryImpl
import com.example.satcomposition.domain.GameRepository
import com.example.satcomposition.domain.entity.Level
import com.example.satcomposition.domain.entity.Question

class GameViewModel(level: Level) : ViewModel() {
    private val repository = GameRepositoryImpl
    private val gameSettings = repository.getGameSettings(level)

    fun generateQuestions (): Question{
       return repository.generateQuestion(gameSettings.maxSumValue, COUNT_OF_OPTIONS)
    }
    companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}