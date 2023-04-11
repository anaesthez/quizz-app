package ru.ennesterov.quizz_app.domain.usecases

import ru.ennesterov.quizz_app.domain.entity.GameSettings
import ru.ennesterov.quizz_app.domain.entity.Level
import ru.ennesterov.quizz_app.domain.repository.GameRepository

class GetGameSettingsUseCase (
    private val repository: GameRepository
        ) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}