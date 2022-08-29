package com.example.satcomposition.domain.entity

import java.io.Serializable

data class GameSettings (
    val maxSumValue : Int,
    val minCountOfRightAnswer : Int,
    val minPercentOfRightAnswer : Int,
    val gameTimeInSecond : Int
        ): Serializable
