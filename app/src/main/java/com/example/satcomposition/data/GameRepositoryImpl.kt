package com.example.satcomposition.data

import com.example.satcomposition.domain.GameRepository
import com.example.satcomposition.domain.entity.GameSettings
import com.example.satcomposition.domain.entity.Level
import com.example.satcomposition.domain.entity.Question
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 7
    private const val MIN_ANSWER_VALUE = 1


    override fun generateQuestion(maxSumValue: Int, countOfOption: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE,maxSumValue+1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE,sum)
        val options = HashSet<Int>()
        val rightAnswer = sum-visibleNumber
        options.add(rightAnswer)
        while(options.size<countOfOption){
            options.add(Random.nextInt(MIN_ANSWER_VALUE,sum))
        }
        return Question(sum,visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings
        {
            return when(level)
            {
                Level.TEST -> {
                    GameSettings(10,
                        3,
                        50,
                        8)}
                Level.EASY -> {
                    GameSettings(10,
                        10,
                        70,
                        60)}
                Level.NORMAL -> {
                    GameSettings(20,
                        20,
                        80,
                        60)}
                Level.HARD -> {
                    GameSettings(100,
                        20,
                        80,
                        60)}
            }
        }
    }
