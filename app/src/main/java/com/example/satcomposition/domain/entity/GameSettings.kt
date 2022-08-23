package com.example.satcomposition.domain.entity

data class GameSettings (
    val maxSumValue : Int,
    val minCountOfRightAnswer : Int,
    val minPercentOfRightAnswer : Int,
    val gameTimeInSecond : Int
        )
