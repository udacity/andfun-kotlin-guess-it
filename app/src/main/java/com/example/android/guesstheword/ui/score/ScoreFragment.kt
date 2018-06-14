package com.example.android.guesstheword.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding

class ScoreFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = ScoreFragment()
    }

    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)
        var root = binding.root

        viewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)
        binding.scoreViewModel = viewModel

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the score
        viewModel.setScore(ScoreFragmentArgs.fromBundle(arguments).score)

        viewModel.eventPlayAgainClicked.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                NavHostFragment.findNavController(this).navigate(R.id.action_restart)
            }
        })
    }

}
