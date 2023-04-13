package ru.ennesterov.quizz_app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.ennesterov.quizz_app.R
import ru.ennesterov.quizz_app.databinding.FragmentGameResultBinding
import ru.ennesterov.quizz_app.domain.entity.GameResult

class GameResultFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        setupViews()
    }

    private fun setUpClickListeners() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.tryAgainButton.setOnClickListener {
            retryGame()
        }
    }

    private fun setupViews() {
        with(binding) {
            resultEmojiIv.setImageResource(getImageState(gameResult.winner))
            requiredPercentage.text = String.format(
                getString(R.string.required_percentage_of_right_answers),
                gameResult.gameSettings.minPercentOfRightAnswers
            )
            requiredAnswers.text = String.format(
                getString(R.string.required_amount_of_right_answers),
                gameResult.gameSettings.minCountOfRightAnswer
            )
            currentAnswers.text = String.format(
                getString(R.string.current_amount_of_answers),
                gameResult.countOfRightAnswers
            )
            currentPercentage.text = String.format(
                getString(R.string.current_percentage_of_right_answers),
                getPercentageOfRightAnswers()
            )
        }
    }

    private fun getPercentageOfRightAnswers() = with(gameResult) {
        if (countOfQuestions == 0)
            0
        else
            (countOfRightAnswers / countOfQuestions.toDouble() * 100).toInt()
    }

    private fun getImageState(winner: Boolean): Int =
        if (winner) R.drawable.happy else R.drawable.sad

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_RESULT_STATE)?.let {
            gameResult = it
        }
    }

    companion object {

        private const val KEY_RESULT_STATE = "KEY_RESULT_STATE"
        fun newInstance(gameResult: GameResult): Fragment {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_RESULT_STATE, gameResult)
                }
            }
        }
    }

}