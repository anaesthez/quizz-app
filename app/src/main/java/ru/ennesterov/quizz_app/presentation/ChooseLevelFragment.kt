package ru.ennesterov.quizz_app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ennesterov.quizz_app.databinding.FragmentChooseLevelBinding
import ru.ennesterov.quizz_app.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testLevelButton.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
        binding.easyLevelButton.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        binding.mediumLevelButton.setOnClickListener {
            launchGameFragment(Level.MEDIUM)
        }
        binding.hardLevelButton.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}