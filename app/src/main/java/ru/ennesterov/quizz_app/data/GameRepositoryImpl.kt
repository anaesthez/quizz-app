package ru.ennesterov.quizz_app.data

import ru.ennesterov.quizz_app.domain.entity.GameSettings
import ru.ennesterov.quizz_app.domain.entity.Level
import ru.ennesterov.quizz_app.domain.entity.Question
import ru.ennesterov.quizz_app.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 5
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val rightAnswer: Int = sum - visibleNumber
        val options = HashSet<Int>()
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue - 1, rightAnswer + countOfOptions)
        while(options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    3,
                50,
                    8
                )
            }
            Level.EASY -> {
                GameSettings(
                    10,
                    3,
                    70,
                    60
                )
            }
            Level.MIDDLE -> {
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }
            Level.HARD -> {
                GameSettings(
                    100,
                    100,
                    80,
                    100
                )
            }
        }
    }

}