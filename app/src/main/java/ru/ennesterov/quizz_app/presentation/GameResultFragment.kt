package ru.ennesterov.quizz_app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ennesterov.quizz_app.R
import ru.ennesterov.quizz_app.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment() {

    private val args by navArgs<GameResultFragmentArgs>()

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

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
        binding.tryAgainButton.setOnClickListener {
            retryGame()
        }
    }

    private fun setupViews() {
        with(binding) {
            resultEmojiIv.setImageResource(getImageState(args.gameResult.winner))
            requiredPercentage.text = String.format(
                getString(R.string.required_percentage_of_right_answers),
                args.gameResult.gameSettings.minPercentOfRightAnswers
            )
            requiredAnswers.text = String.format(
                getString(R.string.required_amount_of_right_answers),
                args.gameResult.gameSettings.minCountOfRightAnswer
            )
            currentAnswers.text = String.format(
                getString(R.string.current_amount_of_answers),
                args.gameResult.countOfRightAnswers
            )
            currentPercentage.text = String.format(
                getString(R.string.current_percentage_of_right_answers),
                getPercentageOfRightAnswers()
            )
        }
    }

    private fun getPercentageOfRightAnswers() = with(args.gameResult) {
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
        findNavController().popBackStack()
    }
}