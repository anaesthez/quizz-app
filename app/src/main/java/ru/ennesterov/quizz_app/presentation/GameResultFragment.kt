package ru.ennesterov.quizz_app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun parseArgs() {
        gameResult = requireArguments().getSerializable(KEY_RESULT_STATE) as GameResult
    }

    companion object {

        private const val KEY_RESULT_STATE = "KEY_RESULT_STATE"
        fun newInstance(gameResult: GameResult): Fragment {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_RESULT_STATE, gameResult)
                }
            }
        }
    }

}