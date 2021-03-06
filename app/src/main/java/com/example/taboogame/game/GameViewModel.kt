package com.example.taboogame.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.example.taboogame.models.GuessWord
import com.example.taboogame.data.WordsToGuessListENGLISH
import com.example.taboogame.data.WordsToGuessListPOLISH
import com.example.taboogame.data.WordsToGuessListSPANISH
import com.example.taboogame.models.NewGameSettings

private val ADD_POINTS_BUZZ_PATTERN = longArrayOf(100, 100, 100)
private val SUBTRACT_POINTS_PATTERN = longArrayOf(0, 100, 100, 100)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel (
    newGameSettings: NewGameSettings
) : ViewModel() {

    private var guessWordList: ArrayList<GuessWord>

    private val guessWordLanguage = newGameSettings.language

    private val DONE = 0L
    private val ONE_SECOND = 1000L
    private var COUNTDOWN_TIME = newGameSettings.roundTime
    private var numberOfSkipsAvailable = newGameSettings.skipAvailable
    private var gamePointsLimit = newGameSettings.pointsLimit
    private val activeVibration = newGameSettings.vibration

    enum class BuzzType(val pattern: LongArray) {
        ADD_POINTS(ADD_POINTS_BUZZ_PATTERN),
        SUBTRACT(SUBTRACT_POINTS_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    var teamOneActive = true
    var teamTwoActive = false
    var pointsInActiveRound = 0

    var timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    private val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime((time))
    }

    private var timeLeftOnPause = COUNTDOWN_TIME
    private var timeToShow = COUNTDOWN_TIME

    private val _nextRoundActive = MutableLiveData<Boolean>()
    val nextRoundActive: LiveData<Boolean>
        get() = _nextRoundActive

    private val _guessWord = MutableLiveData<GuessWord>()
    val guessWord: LiveData<GuessWord>
        get() = _guessWord

    private val _teamOneScore = MutableLiveData<Int>()
    val teamOneScore: LiveData<Int>
        get() = _teamOneScore

    private val _teamTwoScore = MutableLiveData<Int>()
    val teamTwoScore: LiveData<Int>
        get() = _teamTwoScore

    private val _teamOneWordsSkipped = MutableLiveData<Int>()
    val teamOneWordsSkipped: LiveData<Int>
        get() = _teamOneWordsSkipped

    private val _teamTwoWordsSkipped = MutableLiveData<Int>()
    val teamTwoWordsSkipped: LiveData<Int>
        get() = _teamTwoWordsSkipped

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    init {
        guessWordList = setLanguage()
        _guessWord.value = guessWordList[0]
        _teamOneScore.value = 0
        _teamTwoScore.value = 0
        _teamOneWordsSkipped.value = numberOfSkipsAvailable
        _teamTwoWordsSkipped.value = numberOfSkipsAvailable
        _nextRoundActive.value = false
        _gameFinished.value = false
        teamOneActive = true
        teamTwoActive = false

        updateGuessWord()

        timer = object : CountDownTimer(timeLeftOnPause, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                timeToShow -= 1000L
                _currentTime.value = (timeToShow / ONE_SECOND)

                if (timeLeftOnPause <= 0) {
                    timeLeftOnPause = 0
                    endTimer()
                } else {
                    timeLeftOnPause -= 1000L
                }
            }

            override fun onFinish() {
                _currentTime.value = DONE
                switchTeams()
                _nextRoundActive.value = true
                pointsInActiveRound = 0
            }
        }
    }

    fun restartTimer() {
        restartTimerObject()
        _nextRoundActive.value = false
    }

    private fun restartTimerObject() {
        timeLeftOnPause = COUNTDOWN_TIME
        timeToShow = COUNTDOWN_TIME
    }

    private fun endTimer() {
        timer.onFinish()
        timer.cancel()
    }

    fun nextWord() {
        if (guessWordList.isEmpty()) {
            guessWordList = setLanguage()
        }
        pointsInActiveRound++
        updateGuessWord()
        addTeamOneScore()
        addTeamTwoScore()
        isGameFinished()
        buzzAddPoints()
    }

    fun skipWord() {
        if (guessWordList.isEmpty()) {
            guessWordList = setLanguage()
        }
        decreaseSkipWordsAvailable()
        updateGuessWord()
    }

    fun skipWordAndLosePoint() {
        if (guessWordList.isEmpty()) {
            guessWordList = setLanguage()
        }
        pointsInActiveRound--
        updateGuessWord()
        subtractTeamOneScore()
        subtractTeamTwoScore()
        buzzSubtractPoints()
    }

    private fun switchTeams() {
        if (teamOneActive) {
            teamOneActive = false
            teamTwoActive = true
        } else {
            teamOneActive = true
            teamTwoActive = false
        }
    }

    private fun addTeamOneScore() {
        if (teamOneActive) {
            _teamOneScore.value = (teamOneScore.value)?.plus(1)
        }
    }

    private fun subtractTeamOneScore() {
        if (teamOneActive) {
            _teamOneScore.value = (teamOneScore.value)?.minus(1)
        }
    }

    private fun addTeamTwoScore() {
        if (teamTwoActive) {
            _teamTwoScore.value = (teamTwoScore.value)?.plus(1)
        }
    }

    private fun subtractTeamTwoScore() {
        if (teamTwoActive) {
            _teamTwoScore.value = (teamTwoScore.value)?.minus(1)
        }
    }

    private fun updateGuessWord() {
        _guessWord.value = guessWordList[0]
        guessWordList.removeAt(0)
    }

    private fun decreaseSkipWordsAvailable() {
        if (teamOneActive && _teamOneWordsSkipped.value != 0) {
            _teamOneWordsSkipped.value = teamOneWordsSkipped.value?.minus(1)
        }
        if (teamTwoActive && _teamTwoWordsSkipped.value != 0) {
            _teamTwoWordsSkipped.value = teamTwoWordsSkipped.value?.minus(1)
        }
    }

    private fun isGameFinished() {
        if (teamOneScore.value == gamePointsLimit || teamTwoScore.value == gamePointsLimit) {
            _gameFinished.value = true
        }
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    private fun buzzAddPoints() {
        if (activeVibration) {
            _eventBuzz.value = BuzzType.ADD_POINTS
        } else _eventBuzz.value = BuzzType.NO_BUZZ
    }

    private fun buzzSubtractPoints() {
        if (activeVibration) {
            _eventBuzz.value = BuzzType.SUBTRACT
        } else _eventBuzz.value = BuzzType.NO_BUZZ
    }

    private fun setLanguage(): ArrayList<GuessWord> {
        guessWordList = when (guessWordLanguage) {
            "en" -> {
                WordsToGuessListENGLISH.allWordsENGLISH()
            }
            "es" -> {
                WordsToGuessListSPANISH.allWordsSPANISH()
            }
            else -> {
                WordsToGuessListPOLISH.allWordsPOLISH()
            }
        }
        return guessWordList
    }
}