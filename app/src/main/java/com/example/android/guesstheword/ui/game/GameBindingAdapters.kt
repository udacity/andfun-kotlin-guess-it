package com.example.android.guesstheword.ui.game

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("time")
fun setTime(textView: TextView, time: Long) {
    textView.text = DateUtils.formatElapsedTime(time)
}