package com.example.questiongame.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.questiongame.R
import com.example.questiongame.databinding.ActivityPlayQuestionBinding
import com.example.questiongame.utils.Constant.BASEURL
import com.example.questiongame.utils.Constant.color_correct
import com.example.questiongame.utils.Constant.color_wrong
import com.example.questiongame.utils.showProgress

class PlayQuestionActivity : AppCompatActivity(),OnClickListener {

    lateinit var binding: ActivityPlayQuestionBinding
    lateinit var txt_timer: TextView
    var correct_answer = ""
    var isquestionAnswered = false
    lateinit var timer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityPlayQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llOptionA.setOnClickListener(this)
        binding.llOptionB.setOnClickListener(this)
        binding.llOptionC.setOnClickListener(this)
        binding.llOptionD.setOnClickListener(this)
        binding.btnNextquestion.setOnClickListener(this)

        val bundle = intent.extras
        val array = bundle?.getStringArray("LIST")
        binding.txtQuestion.text = array?.get(0)
        binding.txtOptionA.text = array?.get(1)
        binding.txtOptionB.text = array?.get(2)
        binding.txtOptionC.text = array?.get(3)
        binding.txtOptionD.text = array?.get(4)
        correct_answer = array?.get(5).toString()
        Log.e("CORRECT ANSWER",correct_answer)
        if (array?.get(6)!!.isNotEmpty()){
            binding.imageView.visibility = View.VISIBLE
            Glide.with(binding.imageView.context).load(BASEURL+array?.get(6).toString()).error(R.drawable.error_image).into(binding.imageView)
        }else{
            binding.videoView.visibility = View.VISIBLE
            setVideo(binding.videoView, BASEURL+array[7])
        }

         timer = object : CountDownTimer(31000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.txtQueTimer.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
//                showProgress(this@PlayQuestionActivity,false)
            }
        }.start()
    }
    fun View.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 50L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
    }
    fun setVideo(view: VideoView, url: String){
        val uri: Uri = Uri.parse(url)
        view.setVideoURI(uri)
        view.start()
        view.setOnPreparedListener { mp -> mp.isLooping = true }
    }

    override fun onClick(v: View?) {
        fun wrongAnswerAction(ll:LinearLayout,textView: TextView){
            ll.blink(5)
            textView.setBackgroundColor(color_wrong)
            isquestionAnswered = true
            timer.cancel()
            Handler().postDelayed({
                binding.btnNextquestion.visibility = View.VISIBLE
            }, 2000)

        }
        fun correctAnswerAction(textView: TextView){
            textView.setBackgroundColor(color_correct)
        }
        fun wrong_expression(){
            when(v!!.id){
                R.id.ll_optionA -> {
                    wrongAnswerAction(binding.llOptionA,binding.txtOptionA)
                }
                R.id.ll_optionB -> {
                    wrongAnswerAction(binding.llOptionB,binding.txtOptionB)

                }
                R.id.ll_optionC -> {
                    wrongAnswerAction(binding.llOptionC,binding.txtOptionC)

                }
                R.id.ll_optionD -> {
                    wrongAnswerAction(binding.llOptionD,binding.txtOptionD)
                }
            }
        }
        if(v == binding.btnNextquestion){ startActivity(Intent(this,SelectCatQue::class.java)) }
        if (!isquestionAnswered){
            wrong_expression()
        }
        when(correct_answer){
            "A" -> {
                correctAnswerAction(binding.txtOptionA)
            }
            "B" -> {
                correctAnswerAction(binding.txtOptionB)
            }
            "C" -> {
                correctAnswerAction(binding.txtOptionC)
            }
            "D" -> {
                correctAnswerAction(binding.txtOptionD)
            }
        }
    }
}