package com.example.taboogame.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.taboogame.data.GuessWord
import com.example.taboogame.data.WordsToGuessList


class GameViewModel: ViewModel() {

    private var guessWordList: ArrayList<GuessWord>

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val  COUNTDOWN_TIME = 20000L
    }
    
    var timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    private val currentTime: LiveData<Long>
    get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime((time))
    }
    var oneRoundTime = COUNTDOWN_TIME
    var timeLeftOnPause = 0L

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

    val teamOneName = ""
    var teamOneActive = true

    val teamTwoName = ""
    var teamTwoActive = false

    var numberOfSkipsAvailable = 3

    private val _teamOneWordsSkipped = MutableLiveData<Int>()
    val teamOneWordsSkipped: LiveData<Int>
        get() = _teamOneWordsSkipped

    private val _teamTwoWordsSkipped = MutableLiveData<Int>()
    val teamTwoWordsSkipped: LiveData<Int>
        get() = _teamTwoWordsSkipped

    private val _teamOneUsedAllSkipWords = MutableLiveData<Boolean>()
    val teamOneUsedAllSkipWords: LiveData<Boolean>
        get() = _teamOneUsedAllSkipWords

    private val _teamTwoUsedAllSkipWords = MutableLiveData<Boolean>()
    val teamTwoUsedAllSkipWords: LiveData<Boolean>
        get() = _teamTwoUsedAllSkipWords

init {
    guessWordList = WordsToGuessList.allWords()
    _guessWord.value = guessWordList[0]
    _teamOneUsedAllSkipWords.value = false
    _teamTwoUsedAllSkipWords.value = false
    _teamOneScore.value = 0
    _teamTwoScore.value = 0
    _teamOneWordsSkipped.value = numberOfSkipsAvailable
    _teamTwoWordsSkipped.value = numberOfSkipsAvailable
    _nextRoundActive.value = false
     timeLeftOnPause


    updateGuessWord()

    timer = object : CountDownTimer(oneRoundTime, ONE_SECOND) {

        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = (millisUntilFinished / ONE_SECOND)
            timeLeftOnPause = oneRoundTime - 1000L
        }

        override fun onFinish() {
            _currentTime.value = DONE
            _teamOneUsedAllSkipWords.value = false
            _teamTwoUsedAllSkipWords.value = false
            _nextRoundActive.value = true

            if(teamOneActive){
                teamOneActive = false
                teamTwoActive = true
            } else {
                teamOneActive = true
                teamTwoActive = false
            }
        }
    }
    timer.start()
}

    fun restartTimer () {
        oneRoundTime = COUNTDOWN_TIME
        timer.start()
        _nextRoundActive.value = false
    }

     fun nextWord() {
        if (guessWordList.isEmpty()) {
            guessWordList = WordsToGuessList.allWords()
        }
        updateGuessWord()
        addTeamOneScore()
        addTeamTwoScore()
    }

    fun skipWord() {
        if (guessWordList.isEmpty()) {
            guessWordList = WordsToGuessList.allWords()
        } else {
            updateGuessWord()
        }
        disableSkipWords()
    }

    fun skipWordAndLosePoint() {
        if (guessWordList.isEmpty()) {
            guessWordList = WordsToGuessList.allWords()
        } else {
            updateGuessWord()
        }
        substractTeamOneScore()
        substractTeamTwoScore()
    }

    fun updateTimeOnPause() {
        oneRoundTime = timeLeftOnPause

    }

    fun resumeTimer() {
        timer.start()
    }

    private fun addTeamOneScore() {
        if (teamOneActive) {
            _teamOneScore.value = (teamOneScore.value)?.plus(1)
        }
    }

    private fun substractTeamOneScore () {
        if (teamOneActive) {
            _teamOneScore.value = (teamOneScore.value)?.minus(1)
        }
    }

    private fun addTeamTwoScore() {
        if (teamTwoActive) {
            _teamTwoScore.value= (teamTwoScore.value)?.plus(1)
        }
    }

    private fun substractTeamTwoScore () {
        if (teamTwoActive) {
            _teamTwoScore.value= (teamTwoScore.value)?.minus(1)
        }
    }

    private fun updateGuessWord () {
        _guessWord.value = guessWordList[0]
        guessWordList.removeAt(0)
    }

    private fun disableSkipWords(){
        if (teamOneActive){
            if (1 >= _teamOneWordsSkipped.value ?: 0) {
                _teamOneUsedAllSkipWords.value = true
                _teamOneWordsSkipped.value = 0
            }else {
                _teamOneWordsSkipped.value = teamOneWordsSkipped.value?.minus(1)
            }
        }
        if (teamTwoActive){
            if (1 >= _teamTwoWordsSkipped.value ?: 0) {
                _teamTwoUsedAllSkipWords.value = true
                _teamTwoWordsSkipped.value = 0
            }else {
                _teamTwoWordsSkipped.value = teamTwoWordsSkipped.value?.minus(1)
            }
        }
    }
}