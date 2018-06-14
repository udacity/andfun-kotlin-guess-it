package com.example.android.guesstheword.ui.game

import androidx.databinding.BindingAdapter
import android.text.format.DateUtils
import android.widget.TextView


@BindingAdapter("time")
fun setTime(textview: TextView, time: Long) {
    textview.text = DateUtils.formatElapsedTime(time)
}