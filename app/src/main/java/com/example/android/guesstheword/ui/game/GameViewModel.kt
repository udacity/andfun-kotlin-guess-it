package com.example.android.guesstheword.ui.game

import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.guesstheword.Event


class GameViewModel : ViewModel() {

    enum class BuzzType {
        CORRECT, GAME_OVER, COUNTDOWN_PANIC
    }

    companion object {
        const val DONE = 0L
        const val COUNTDOWN_PANIC_SECONDS = 10L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 15000L
    }

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() {
            return _currentTime
        }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() {
            return _word
        }

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() {
            return _score
        }

    private var startTime: Long = SystemClock.elapsedRealtime()

    private var wordList = mutableListOf("Yodeling",
            "Skiing",
            "Punching",
            "Walking your cat",
            "Studying",
            "Deep sea Fishing",
            "Lounging",
            "Sky diving")

    private val _eventGameFinished = MutableLiveData<Event<Int>>()
    val eventGameFinished: LiveData<Event<Int>>
        get() = _eventGameFinished

    private val _eventBuzzer = MutableLiveData<Event<BuzzType>>()
    val eventBuzzer: LiveData<Event<BuzzType>>
        get() = _eventBuzzer

    init {
        resetList()
        nextWord()
        _score.value = 0

        object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.postValue(millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzzer.postValue(Event(BuzzType.COUNTDOWN_PANIC))
                }
            }

            override fun onFinish() {
                _currentTime.postValue(DONE)
                _eventBuzzer.postValue(Event(BuzzType.GAME_OVER))
                _eventGameFinished.postValue(Event(score.value ?: 0))
            }
        }.start()
    }

    private fun resetList() {
        wordList.shuffle()
    }

    fun onSkipClicked(view: View) {
        _score.value = (_score.value)?.minus(1)
        nextWord()

    }

    fun onCorrectClicked(view: View) {
        _score.value = (_score.value)?.plus(1)
        _eventBuzzer.value = Event(BuzzType.CORRECT)
        nextWord()
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }
}
