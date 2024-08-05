package com.example.questiongame.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.questiongame.R
import com.example.questiongame.databinding.ActivityTestBinding
import com.example.questiongame.utils.Constant.BASEURL
import com.example.questiongame.utils.Constant.color_correct


class Test : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    lateinit var videoView: VideoView
    var videoUrl = BASEURL+"storage/questions/videos/4RP771618909995232.m4v"
    lateinit var txt_timer:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.videoView.visibility = View.GONE
//        val bitmap: Bitmap = Glide.with(this).asBitmap().load(BASEURL+"storage/questions/images/Dodecahedron.jpg").submit().get()
//        binding.topLeft.background = bitmap.toDrawable(resources)
        Glide.with(binding.imgView.context).load(BASEURL+"storage/questions/images/QC002207.jpg").into(binding.imgView)
        binding.imgView.scaleType = ImageView.ScaleType.FIT_XY
        //        videoView = findViewById(R.id.video_view)
//        val uri: Uri = Uri.parse(videoUrl)
//        videoView.setVideoURI(uri)
//        videoView.start()
//        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        txt_timer= findViewById(R.id.txt_que_timer)

        object : CountDownTimer(11000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                txt_timer.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
            }
        }.start()

        val optionA:LinearLayout = findViewById(R.id.ll_optionA)
        optionA.setOnClickListener {
            optionA.blink(5)
            binding.txtOptionA.setBackgroundColor(color_correct)
        }

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
}