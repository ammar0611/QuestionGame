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
import com.questions.game.R
import com.questions.game.app.getquestion.view.GetQuestionDetails
import com.questions.game.databinding.ActivityPlayQuestionBinding
import com.questions.game.app.spinwheel.SelectCatQue
import com.questions.game.utils.Constant.BASEURL
import com.questions.game.utils.Utils.blink
import com.questions.game.utils.Constant.color_correct
import com.questions.game.utils.Constant.color_wrong
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.Pref
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
    lateinit var bg_music:MediaPlayer
    lateinit var correct_sound:MediaPlayer
    lateinit var wrong_sound:MediaPlayer
    lateinit var last_4sec:MediaPlayer
    lateinit var time_out:MediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityPlayQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        setGamePoints()
        is15sec = true

        bg_music = MediaPlayer.create(this,R.raw.bg_normal_sound)
        correct_sound = MediaPlayer.create(this,R.raw.correct_answer)
        wrong_sound = MediaPlayer.create(this,R.raw.wrong_answer)
        last_4sec = MediaPlayer.create(this,R.raw.last_4sec)
        time_out = MediaPlayer.create(this,R.raw.time_out)

        bg_music.start()
        bg_music.isLooping = true

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
                    if (!last_4sec.isPlaying) {  // Ensure the sound is not already playing
                        last_4sec.start()
                    }
                }
            }

            override fun onFinish() {
                time_out.start()
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
        binding.txtPoints.text = Pref.getIntValue("gamePoints").toString()
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
                    timer?.cancel()
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
            correct_sound.start()
            textView.setBackgroundColor(color_correct)
            Pref.setIntValue("gamePoints", Pref.getIntValue("gamePoints") + 10)
            e("PREF", "Pref + 10")
        } else {
            if (Pref.getIntValue("gamePoints") > 0) {
                Pref.setIntValue("gamePoints", Pref.getIntValue("gamePoints") - 1)
            }
            e("PREF", "Pref - 1")
            ll.blink(5)
            textView.setBackgroundColor(color_wrong)
            wrong_sound.start()
            when (correctAnswer) {
                "A" -> {
                    binding.txtOptionA.setBackgroundColor(color_correct)
                }

                "B" -> {
                    binding.txtOptionB.setBackgroundColor(color_correct)
                }

                "C" -> {
                    binding.txtOptionC.setBackgroundColor(color_correct)
                }

                "D" -> {
                    binding.txtOptionD.setBackgroundColor(color_correct)
                }
            }
        }

        isQuestionAnswered = true
        timer.cancel()
        Handler().postDelayed({
            startActivity(Intent(this, SelectCatQue::class.java))
        }, 2000)

        setGamePoints()
        val list = Pref.getList("askedQuestion").toMutableList()
        if (list.isEmpty()) {
            val newList: Set<String> = setOf(questionId)
            Pref.setList("askedQuestion", newList)
        } else {
            list.add(questionId)
            Pref.setList("askedQuestion", list.toSet())
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SelectCatQue::class.java))
        finish()
    }
    override fun onStop() {
        super.onStop()
        bg_music.release()
    }
}
