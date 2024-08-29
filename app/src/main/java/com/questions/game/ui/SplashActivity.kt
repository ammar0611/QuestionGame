package com.questions.game.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.questions.game.R
import com.questions.game.utils.Constant
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.Pref
import com.questions.game.utils.Utils

class SplashActivity : AppCompatActivity() {
    private lateinit var UDID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        Pref.initialize(this)
        initView()
    }


    private fun initView() {
        UDID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Pref.setValue(Constant.userIdPref, UDID)
        checkUserIdInCollection()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun checkUserIdInCollection() {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(Constant.dbUserTable).get().addOnSuccessListener { result ->
                var userExists = false
                for (document in result) {
                    val userId = document.getString(Constant.USER_ID)
                    if (userId == UDID) {
                        userExists = true
                        break
                    }
                }
                if (userExists) {
                    e("Firestore Log", "User Exists")
                    readDataFromFirestore()
                } else {
                    e("Firestore Log", "User does not Exist")
                    addEmptyDataToFirestore()
                }
            }.addOnFailureListener { e -> e("Firestore Log", "Error getting documents: $e") }
    }

    private fun readDataFromFirestore() {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(Constant.dbUserTable).whereEqualTo(Constant.USER_ID, UDID).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    e("Firestore Log", "${document.id} => ${document.data}")
                    Pref.setValue(Constant.userIdPref, document.data[Constant.USER_ID].toString())
                    Pref.setValue(
                        Constant.userNamePref, document.data[Constant.USER_NAME].toString()
                    )
                    Pref.setIntValue(
                        Constant.userScorePref, document.data[Constant.SCORE].toString().toInt()
                    )
                    Pref.setIntValue(
                        Constant.userAssistAmountPref,
                        document.data[Constant.ASSIST_AMOUNT].toString().toInt()
                    )
                    Pref.setIntValue(
                        Constant.userCoinsAmountPref,
                        document.data[Constant.COINS_AMOUNT].toString().toInt()
                    )
                    Pref.setIntValue(
                        Constant.userCurrentLevelPref,
                        document.data[Constant.CURRENT_LEVEL].toString().toInt()
                    )
                    Pref.setValue(
                        Constant.userAskedQuestionPref,
                        document.data[Constant.ANSWERED_QUESTION].toString()
                    )
                    Pref.setIntValue(
                        Constant.userTotalCorrectAnswerPref,
                        document.data[Constant.TOTAL_CORRECT_ANSWER].toString().toInt()
                    )
                    Pref.setIntValue(
                        Constant.userTotalWrongAnswerPref,
                        document.data[Constant.TOTAL_WRONG_ANSWER].toString().toInt()
                    )
                    Pref.setIntValue(
                        Constant.userTotalPlayedQuestionPref,
                        document.data[Constant.TOTAL_PLAYED_QUESTION].toString().toInt()
                    )
                    Pref.setValue(
                        Constant.userSavedGamesPref, document.data[Constant.SAVED_GAMES].toString()
                    )
                }
            }.addOnFailureListener { e -> e("Firestore Log", "Error getting documents => $e") }
    }


    private fun addEmptyDataToFirestore() {
        val name = Utils.adjective.random() + " " + Utils.animals.random()
        val emptyAskedQuestion: MutableMap<Int, MutableList<Int>> = mutableMapOf(
            1 to mutableListOf(),
            2 to mutableListOf(),
            3 to mutableListOf(),
            4 to mutableListOf(),
            5 to mutableListOf(),
            6 to mutableListOf()
        )
        val firestore = FirebaseFirestore.getInstance()
        val user = HashMap<String, Any>()
        user[Constant.USER_ID] = UDID
        user[Constant.USER_NAME] = name
        user[Constant.SCORE] = 0
        user[Constant.ASSIST_AMOUNT] = 0
        user[Constant.COINS_AMOUNT] = 0
        user[Constant.CURRENT_LEVEL] = 0
        user[Constant.ANSWERED_QUESTION] = Utils.mapToString(emptyAskedQuestion)
        user[Constant.TOTAL_CORRECT_ANSWER] = 0
        user[Constant.TOTAL_WRONG_ANSWER] = 0
        user[Constant.TOTAL_PLAYED_QUESTION] = 0
        user[Constant.SAVED_GAMES] = " "
        user[Constant.LAST_LOGIN_TIME] = Utils.getCurrentTimestampInIST()
        firestore.collection(Constant.dbUserTable).document().set(user).addOnSuccessListener {
                e("Firestore Log", "Empty DocumentSnapshot successfully written!")
            }.addOnFailureListener { e -> e("Firestore Log", "Error writing document: $e") }
    }
}