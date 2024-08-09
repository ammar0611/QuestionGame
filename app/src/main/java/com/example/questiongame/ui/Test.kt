package com.example.questiongame.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import com.bluehomestudio.luckywheel.WheelItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.caneryilmaz.apps.luckywheel.data.WheelData
import com.example.questiongame.R
import com.example.questiongame.databinding.ActivityTestBinding
import com.example.questiongame.utils.Constant.BASEURL
import com.example.questiongame.utils.Constant.color_correct
import com.example.questiongame.utils.Pref
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class Test : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val wheeldata = ArrayList<WheelData>()
        wheeldata.add(
            WheelData("quiz1", Color.BLACK, Color.RED,
                BitmapFactory.decodeResource(resources, R.drawable.quiz_1))
        )
        wheeldata.add(WheelData("school2", Color.BLACK, Color.GRAY,
            BitmapFactory.decodeResource(resources, R.drawable.school_2)))
        wheeldata.add(WheelData("quick3", Color.BLACK, Color.BLUE,
            BitmapFactory.decodeResource(resources, R.drawable.quick_3)))
        wheeldata.add(WheelData("music4", Color.BLACK, Color.CYAN,
            BitmapFactory.decodeResource(resources, R.drawable.music_4)))
        wheeldata.add(WheelData("tv5", Color.BLACK, Color.MAGENTA,
            BitmapFactory.decodeResource(resources, R.drawable.tv_5)))
        wheeldata.add(WheelData("news6", Color.BLACK, Color.YELLOW,
            BitmapFactory.decodeResource(resources, R.drawable.news_6)))
        wheeldata.add(WheelData("random", Color.BLACK, Color.GREEN,
            BitmapFactory.decodeResource(resources, R.drawable.random_7)))
        binding.wheel.setWheelData(wheeldata)
        binding.btnSpin.setOnClickListener {
            binding.wheel.rotateWheel()
        }


        val list = listOf(0.1,0.2,0.1,0.5,0.3,0.1,2.0)
        val sortedList=list.sorted()
        Log.e("sorted list",sortedList.toString())

        var cummlative_prob = 0.0
        var prev_i = 0.0
        val random1 = Random.nextDouble(0.1,3.3 )
        val random = String.format("%.1f", random1).toDouble()
        Log.e("Random num",random.toString())
        for(i in sortedList){
            cummlative_prob += i
            if(cummlative_prob >= random){
                if (cummlative_prob > random){
                    binding.wheel.setTarget(target = list.indexOf(prev_i))
                    break
                }
                binding.wheel.setTarget(target = list.indexOf(i))
                break
            }
            prev_i = i
        }
        binding.wheel.setTargetReachListener { wheeldata ->
            Log.e("wheel", wheeldata.text)
        }

    }
}