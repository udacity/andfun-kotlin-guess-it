package com.example.android.guesstheword.ui.score

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.guesstheword.Event

class ScoreViewModel : ViewModel() {

    private val _eventPlayAgainClicked = MutableLiveData<Event<Boolean>>()
    val eventPlayAgainClicked: LiveData<Event<Boolean>>
        get() = _eventPlayAgainClicked

    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() { return _score}

    fun setScore(score: Int) {
        _score.value = score
    }

    fun onPlayAgainClicked(view : View) {
        _eventPlayAgainClicked.value = Event(true)
    }
}
