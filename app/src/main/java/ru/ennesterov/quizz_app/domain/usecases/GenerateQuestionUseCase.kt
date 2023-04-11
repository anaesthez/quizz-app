package ru.ennesterov.quizz_app.domain.usecases

import ru.ennesterov.quizz_app.domain.entity.Question
import ru.ennesterov.quizz_app.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {

        private const val COUNT_OF_OPTIONS = 4
    }
}