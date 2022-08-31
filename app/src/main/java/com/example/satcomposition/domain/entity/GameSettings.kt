package com.example.satcomposition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameSettings (
    val maxSumValue : Int,
    val minCountOfRightAnswer : Int,
    val minPercentOfRightAnswer : Int,
    val gameTimeInSecond : Int
        ): Parcelable
