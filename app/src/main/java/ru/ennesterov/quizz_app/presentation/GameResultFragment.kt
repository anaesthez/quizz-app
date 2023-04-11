package ru.ennesterov.quizz_app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ennesterov.quizz_app.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment() {

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}