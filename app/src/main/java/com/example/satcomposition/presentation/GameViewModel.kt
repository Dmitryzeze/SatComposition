package com.example.satcomposition.presentation

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.satcomposition.R
import com.example.satcomposition.data.GameRepositoryImpl
import com.example.satcomposition.domain.entity.GameResult
import com.example.satcomposition.domain.entity.GameSettings
import com.example.satcomposition.domain.entity.Level
import com.example.satcomposition.domain.entity.Question
import com.example.satcomposition.domain.interactors.GenerateQuestionInteractor
import com.example.satcomposition.domain.interactors.GetGameSettingsInteractor

class GameViewModel(application: Application, private val level: Level) : AndroidViewModel(application) {
    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null
    private var countOfRightAnswer: Int = 0
    private var countOfQuestions: Int = 0

    private val context = application

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var _enoughPercentOfRightAnswer = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswer: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswer

    private var _enoughCountOfRightAnswer = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswer: LiveData<Boolean>
        get() = _enoughCountOfRightAnswer

    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer


    private var _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private val repository = GameRepositoryImpl


    private val generateQuestionInteractor = GenerateQuestionInteractor(repository)
    private val getGameSettingsInteractor = GetGameSettingsInteractor(repository)

    private fun generateQuestion() {
        val maxSum = gameSettings.maxSumValue
        _question.value = generateQuestionInteractor(maxSum)
    }

    fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = if (countOfQuestions==0){
            0
        }
            else calculatePercentOfRightPercent()
        _percentOfRightAnswer.value = percent
        _progressAnswer.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswer
        )
        _enoughCountOfRightAnswer.value = countOfRightAnswer >= gameSettings.minCountOfRightAnswer
        _enoughPercentOfRightAnswer.value = percent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calculatePercentOfRightPercent(): Int {
        return ((countOfRightAnswer / countOfQuestions.toDouble()) * 100).toInt()


    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswer++
        }
        countOfQuestions++
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            (gameSettings.gameTimeInSecond * MILLIS_IN_SECOND), MILLIS_IN_SECOND
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun formatTime(millisTime: Long): String {
        val seconds = millisTime / MILLIS_IN_SECOND
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftOfSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftOfSeconds)

    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswer.value == true &&
                    enoughPercentOfRightAnswer.value == true,
            countOfRightAnswer,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsInteractor(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }
init {
    startGame()
}
    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}