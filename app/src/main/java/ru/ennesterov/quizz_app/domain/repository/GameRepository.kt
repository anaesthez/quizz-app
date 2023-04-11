package ru.ennesterov.quizz_app.domain.repository

import ru.ennesterov.quizz_app.domain.entity.GameSettings
import ru.ennesterov.quizz_app.domain.entity.Level
import ru.ennesterov.quizz_app.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}