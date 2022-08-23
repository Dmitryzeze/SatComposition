package com.example.satcomposition.domain

import com.example.satcomposition.domain.entity.GameSettings
import com.example.satcomposition.domain.entity.Level
import com.example.satcomposition.domain.entity.Question
import com.example.satcomposition.domain.interactors.GenerateQuestionInteractor
import kotlin.random.Random

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOption : Int
    ) : Question

    fun getGameSettings(level: Level): GameSettings
}