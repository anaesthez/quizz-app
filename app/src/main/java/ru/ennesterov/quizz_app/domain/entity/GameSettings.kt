package ru.ennesterov.quizz_app.domain.entity

data class GameSettings (
    val maxSumValue: Int,
    val minCountOfRightAnswer: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
        )