package ru.ennesterov.quizz_app.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ru.ennesterov.quizz_app.R
import ru.ennesterov.quizz_app.databinding.FragmentGameBinding
import ru.ennesterov.quizz_app.domain.entity.GameResult
import ru.ennesterov.quizz_app.domain.entity.GameSettings
import ru.ennesterov.quizz_app.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.option1.setOnClickListener {
            launchGameResultFragment(GameResult(true, 1, 1, GameSettings(0,0,0,0)))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun parseArgs() {

        level = requireArguments().getSerializable(KEY_LEVEL) as Level

    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameResultFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }
    companion object {

        const val NAME = "GAME_FRAGMENT_NAME"
        private const val KEY_LEVEL = "KEY_LEVEL"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }

}