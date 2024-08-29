package com.questions.game.ui

data class UserDetails(
    val userId: String = "",
    val userName: String = "",
    val score: Int = 1,
    val assistAmount: Int = 1,
    val coinsAmount: Int = 1,
    val currentLevel: Int = 1,
    val answeredQuestionsList: String = "",
    val totalCorrectedAnswer: Int = 1,
    val totalWrongAnswer: Int = 1,
    val totalPlayedQuestions: Int = 1,
    val savedGames: String = "",
    val lastLoginTime: String = ""
)
