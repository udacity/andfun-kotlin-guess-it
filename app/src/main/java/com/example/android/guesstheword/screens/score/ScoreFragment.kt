package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        // Add observer for score
        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }

        // Navigate back to title when button is pressed
        viewModel.eventPlayAgain.observe(this, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })


        return binding.root
    }
}