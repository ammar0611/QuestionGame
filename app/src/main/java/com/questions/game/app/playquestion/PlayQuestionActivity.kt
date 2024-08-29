package com.questions.game.app.playquestion

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.questions.game.R
import com.questions.game.app.getquestion.view.GetQuestionDetails
import com.questions.game.databinding.ActivityPlayQuestionBinding
import com.questions.game.app.spinwheel.SelectCatQue
import com.questions.game.utils.Constant
import com.questions.game.utils.Constant.BASEURL
import com.questions.game.utils.Utils.blink
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.Pref
import com.questions.game.utils.Utils
import com.questions.game.utils.Utils.is15sec
import com.questions.game.utils.Utils.isSkippable

class PlayQuestionActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityPlayQuestionBinding
    private var correctAnswer = ""
    private var isQuestionAnswered = false
    private var timeRemaining: Long = 31000
    private lateinit var timer: CountDownTimer
    private lateinit var questionId: String
    private lateinit var categoryId: String
    private lateinit var bgMusic: MediaPlayer
    private lateinit var correctSound: MediaPlayer
    private lateinit var wrongSound: MediaPlayer
    lateinit var last4sec: MediaPlayer
    lateinit var timeOut: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityPlayQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Pref.initialize(this)
        initView()

    }

    private fun initView() {
        setGamePoints()
        is15sec = true

        bgMusic = MediaPlayer.create(this, R.raw.bg_normal_sound)
        correctSound = MediaPlayer.create(this, R.raw.correct_answer)
        wrongSound = MediaPlayer.create(this, R.raw.wrong_answer)
        last4sec = MediaPlayer.create(this, R.raw.last_4sec)
        timeOut = MediaPlayer.create(this, R.raw.time_out)

        bgMusic.start()
        bgMusic.isLooping = true

        binding.llOptionA.setOnClickListener(this)
        binding.llOptionB.setOnClickListener(this)
        binding.llOptionC.setOnClickListener(this)
        binding.llOptionD.setOnClickListener(this)
        binding.btnAdd15s.setOnClickListener(this)
        if (isSkippable) {
            binding.btnSkip.setOnClickListener(this)
            isSkippable = false
        }
        binding.btnNextquestion.setOnClickListener {
            startActivity(Intent(this, SelectCatQue::class.java))
        }

        binding.btn5050.setOnClickListener {
            remove2option()
        }

        val bundle = intent.extras
        val array = bundle?.getStringArray("LIST")
        categoryId = array?.get(9).toString()
        questionId = array?.get(8).toString()
        e("qID", questionId)
        binding.txtQuestion.text = array?.get(0)
        binding.txtOptionA.text = array?.get(1)
        binding.txtOptionB.text = array?.get(2)
        binding.txtOptionC.text = array?.get(3)
        binding.txtOptionD.text = array?.get(4)
        correctAnswer = array?.get(5).toString()
        e("CORRECT ANSWER", correctAnswer)
        if (array?.get(6)!!.isNotEmpty()) {
            binding.videoView.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
            Glide.with(binding.imageView.context).load(BASEURL + array[6].toString()).centerCrop()
                .error(R.drawable.error_image).into(binding.imageView)
        } else {
            binding.imageView.visibility = View.GONE
            binding.videoView.visibility = View.VISIBLE
            setVideo(binding.videoView, BASEURL + array[7])
        }

        startTimer(timeRemaining)
    }

    private fun startTimer(duration: Long) {
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                binding.txtQueTimer.text = (millisUntilFinished / 1000).toString()
                if (millisUntilFinished in 4000..4999) {
                    if (!last4sec.isPlaying) {  // Ensure the sound is not already playing
                        last4sec.start()
                    }
                }
            }

            override fun onFinish() {
                timeOut.start()
            }
        }.start()
    }

    private fun setVideo(view: VideoView, url: String) {
        val uri: Uri = Uri.parse(url)
        view.setVideoURI(uri)
        view.start()
        view.setOnPreparedListener { mp -> mp.isLooping = true }
    }

    private fun setGamePoints() {
        binding.txtPoints.text = Pref.getIntValue(Constant.userScorePref).toString()
    }

    private fun remove2option() {
        val list = listOf("A", "B", "C", "D")
        var j = 0
        for (i in list.shuffled()) {
            if (i == correctAnswer) {
                continue
            } else {
                if (j > 1) {
                    break
                }
                j++
                when (i) {
                    "A" -> {
                        binding.llOptionA.visibility = View.INVISIBLE
                    }

                    "B" -> {
                        binding.llOptionB.visibility = View.INVISIBLE
                    }

                    "C" -> {
                        binding.llOptionC.visibility = View.INVISIBLE
                    }

                    "D" -> {
                        binding.llOptionD.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (isQuestionAnswered) return

        when (v?.id) {
            R.id.ll_optionA -> handleAnswer(binding.llOptionA, binding.txtOptionA, "A")
            R.id.ll_optionB -> handleAnswer(binding.llOptionB, binding.txtOptionB, "B")
            R.id.ll_optionC -> handleAnswer(binding.llOptionC, binding.txtOptionC, "C")
            R.id.ll_optionD -> handleAnswer(binding.llOptionD, binding.txtOptionD, "D")
            R.id.btn_skip -> {
                val bundle = Bundle()
                bundle.putString("category", categoryId)
                startActivity(Intent(this, GetQuestionDetails::class.java).putExtras(bundle))
            }

            R.id.btn_add15s -> {
                if (is15sec) {
                    timer.cancel()
                    startTimer(timeRemaining + 15000)
                    is15sec = false
                }
            }
        }
    }

    private fun handleAnswer(ll: LinearLayout, textView: TextView, answer: String) {
        if (correctAnswer == answer) {
            binding.lottieView.visibility = View.VISIBLE
            binding.lottieView.bringToFront()
            binding.lottieView.playAnimation()
            ll.blink(5)
            correctSound.start()
            textView.setBackgroundColor(getResources().getColor(R.color.correct_green))
            Pref.setIntValue(Constant.userScorePref, Pref.getIntValue(Constant.userScorePref) + 10)
            Pref.setIntValue(
                Constant.userTotalCorrectAnswerPref,
                Pref.getIntValue(Constant.userTotalCorrectAnswerPref) + 1
            )
            e("PREF", "Pref + 10")
        } else {
            if (Pref.getIntValue(Constant.userScorePref) > 0) {
                Pref.setIntValue(
                    Constant.userScorePref,
                    Pref.getIntValue(Constant.userScorePref) - 1
                )
            }
            Pref.setIntValue(
                Constant.userTotalWrongAnswerPref,
                Pref.getIntValue(Constant.userTotalWrongAnswerPref) + 1
            )
            e("PREF", "Pref - 1")
            ll.blink(5)
            textView.setBackgroundColor(getResources().getColor(R.color.wrong_red))
            wrongSound.start()
            when (correctAnswer) {
                "A" -> {
                    binding.txtOptionA.setBackgroundColor(getResources().getColor(R.color.correct_green))
                }

                "B" -> {
                    binding.txtOptionB.setBackgroundColor(getResources().getColor(R.color.correct_green))
                }

                "C" -> {
                    binding.txtOptionC.setBackgroundColor(getResources().getColor(R.color.correct_green))
                }

                "D" -> {
                    binding.txtOptionD.setBackgroundColor(getResources().getColor(R.color.correct_green))
                }
            }
        }

        isQuestionAnswered = true
        timer.cancel()
        Handler().postDelayed({
            startActivity(Intent(this, SelectCatQue::class.java))
        }, 2000)

        setGamePoints()
        val userAskedList = Utils.stringToMap(Pref.getValue(Constant.userAskedQuestionPref))
        userAskedList[categoryId.toInt()]?.add(questionId.toInt())
        Pref.setValue(Constant.userAskedQuestionPref, Utils.mapToString(userAskedList))
        updateDocumentByUserId(
            mapOf(
                Constant.SCORE to Pref.getIntValue(Constant.userScorePref),
                Constant.ASSIST_AMOUNT to Pref.getIntValue(Constant.userAssistAmountPref),
                Constant.COINS_AMOUNT to Pref.getIntValue(Constant.userCoinsAmountPref),
                Constant.CURRENT_LEVEL to Pref.getIntValue(Constant.userCurrentLevelPref),
                Constant.ANSWERED_QUESTION to Pref.getValue(Constant.userAskedQuestionPref),
                Constant.TOTAL_CORRECT_ANSWER to Pref.getIntValue(Constant.userTotalCorrectAnswerPref),
                Constant.TOTAL_WRONG_ANSWER to Pref.getIntValue(Constant.userTotalWrongAnswerPref),
                Constant.TOTAL_PLAYED_QUESTION to Pref.getIntValue(Constant.userTotalPlayedQuestionPref),
                Constant.LAST_LOGIN_TIME to Utils.getCurrentTimestampInIST()
            )
        )
    }

    private fun updateDocumentByUserId(updatedData: Map<String, Any>) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(Constant.dbUserTable)
            .whereEqualTo(Constant.USER_ID, Pref.getValue(Constant.userIdPref))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    firestore.collection(Constant.dbUserTable).document(document.id)
                        .set(updatedData, SetOptions.merge())
                        .addOnSuccessListener {
                            e(
                                "Firestore Log",
                                "Document ${document.id} successfully UPDATED!"
                            )
                        }
                        .addOnFailureListener { e ->
                            e(
                                "Firestore Log",
                                "Error updating document ${document.id}: $e"
                            )
                        }
                }
            }
            .addOnFailureListener { e -> e("Firestore Log", "Error getting documents: $e") }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SelectCatQue::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        bgMusic.release()
    }
}
