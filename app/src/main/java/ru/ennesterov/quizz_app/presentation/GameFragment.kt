package ru.ennesterov.quizz_app.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.ennesterov.quizz_app.R
import ru.ennesterov.quizz_app.databinding.FragmentGameBinding
import ru.ennesterov.quizz_app.domain.entity.GameResult
import ru.ennesterov.quizz_app.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level
    private val viewModelFactory: GameViewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val optionsTextView by lazy {
        mutableListOf<TextView>().apply {
            add(binding.option1)
            add(binding.option2)
            add(binding.option3)
            add(binding.option4)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

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
        observeViewModel()
        setClickListenersOnOptions()
    }

    private fun setClickListenersOnOptions() {
        optionsTextView.forEach { textView ->
            textView.setOnClickListener {
                viewModel.chooseAnswer(textView.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.timer.text = it
        }
        viewModel.question.observe(viewLifecycleOwner) { question ->
            binding.visibleNumber.text = question.visibleNumber.toString()
            binding.sum.text = question.sum.toString()
            optionsTextView.forEachIndexed { index, textView ->
                textView.text = question.options[index].toString()
            }
        }
        viewModel.percentageOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.percentageTv.setTextColor(getColorState(it))
        }
        viewModel.enoughPercentageOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.progressTintList = ColorStateList.valueOf(getColorState(it))
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.percentageTv.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameResultFragment(it)
        }
    }

    private fun getColorState(appropriateState: Boolean): Int {
        return ContextCompat.getColor(
            requireContext(),
            if (appropriateState)
                android.R.color.holo_green_light
            else
                android.R.color.holo_red_light
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
                level = it
        }
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
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}