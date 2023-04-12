package ru.ennesterov.quizz_app.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {

    TEST, EASY, MEDIUM, HARD
}