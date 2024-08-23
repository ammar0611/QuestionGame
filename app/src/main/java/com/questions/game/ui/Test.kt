package com.questions.game.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.questions.game.R
import com.questions.game.databinding.ActivityTestBinding
import com.questions.game.utils.LogUtil.e


class Test : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}