package com.questions.game.utils


object Constant{

    const val BASEURL="https://arshikweb.com/"
    const val PREF_NAME="MyPref"

    val selectedItems = mutableSetOf<Int>()

//    Preferences keys
    const val userDetailPref = "userDetails"
    const val isPointsPref = "isPoints"
    const val ageIdPref = "ageId"
    const val gamePointsPref = "gamePoints"

    const val userIdPref = "userId"
    const val userNamePref = "userName"
    const val userScorePref = "userScore"
    const val userAssistAmountPref = "userAssistAmount"
    const val userCoinsAmountPref = "userCoinsAmount"
    const val userCurrentLevelPref = "userCurrentLevel"
    const val userAskedQuestionPref = "askedQuestion"
    const val userTotalCorrectAnswerPref = "userTotalCorrectAnswer"
    const val userTotalWrongAnswerPref = "userTotalWrongAnswer"
    const val userTotalPlayedQuestionPref = "userTotalPlayedQuestion"
    const val userSavedGamesPref = "userSavedGames"


//    Firestore Database
    const val dbUserTable="user_info"
    const val USER_ID="userId"
    const val USER_NAME="userName"
    const val SCORE="score"
    const val ASSIST_AMOUNT="assistAmount"
    const val COINS_AMOUNT="coinsAmount"
    const val CURRENT_LEVEL="currentLevel"
    const val ANSWERED_QUESTION="answeredQuestionsList"
    const val TOTAL_CORRECT_ANSWER="totalCorrectedAnswer"
    const val TOTAL_WRONG_ANSWER="totalWrongAnswer"
    const val TOTAL_PLAYED_QUESTION="totalPlayedQuestions"
    const val SAVED_GAMES="savedGames"
    const val LAST_LOGIN_TIME="lastLoginTime"
}