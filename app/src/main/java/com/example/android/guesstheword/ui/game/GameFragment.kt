package com.example.android.guesstheword.ui.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.Event
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding


class GameFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
    val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
    val GAMEOVER_BUZZ_PATTERN = longArrayOf(0, 2000)

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        // Inflate view and obtain an instance of the binding class.
        val binding: GameFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        var root = binding.root

        binding.gameViewModel = viewModel

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.eventGameFinished.observe(this, Observer<Event<Int>> {
            it?.getContentIfNotHandled()?.let { score ->
                val action = GameFragmentDirections.action_game_to_score()
                action.setScore(score)
                findNavController(this).navigate(action)
            }
        })

        viewModel.eventBuzzer.observe(this, Observer {
            it?.getContentIfNotHandled()?.let { buzzType ->
                when (buzzType) {
                    GameViewModel.BuzzType.CORRECT -> buzz(CORRECT_BUZZ_PATTERN)
                    GameViewModel.BuzzType.COUNTDOWN_PANIC -> buzz(PANIC_BUZZ_PATTERN)
                    GameViewModel.BuzzType.GAME_OVER -> buzz(GAMEOVER_BUZZ_PATTERN)
                }
            }
        })
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //TODO review this deprecated method
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }
}
