package com.example.satcomposition.domain.interactors

import com.example.satcomposition.domain.GameRepository
import com.example.satcomposition.domain.entity.GameSettings
import com.example.satcomposition.domain.entity.Question

class GenerateQuestionInteractor(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {

        private const val COUNT_OF_OPTIONS = 6
    }
}