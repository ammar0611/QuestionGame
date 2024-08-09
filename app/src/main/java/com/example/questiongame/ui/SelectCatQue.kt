package com.example.questiongame.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caneryilmaz.apps.luckywheel.data.WheelData
import com.example.questiongame.R
import com.example.questiongame.app.category.view.CategoryActivity
import com.example.questiongame.app.getquestion.view.GetQuestionDetails
import com.example.questiongame.databinding.ActivitySelectCatQueBinding
import kotlin.random.Random

class SelectCatQue : AppCompatActivity(){
    private lateinit var binding:ActivitySelectCatQueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCatQueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wheeldata = ArrayList<WheelData>()
        wheeldata.add(
            WheelData("quiz1", Color.BLACK, Color.RED,
                BitmapFactory.decodeResource(resources, R.drawable.quiz_1))
        )
        wheeldata.add(WheelData("school2", Color.BLACK, Color.GREEN,
            BitmapFactory.decodeResource(resources, R.drawable.school_2)))
        wheeldata.add(WheelData("quick3", Color.BLACK, Color.BLUE,
            BitmapFactory.decodeResource(resources, R.drawable.quick_3)))
        wheeldata.add(WheelData("music4", Color.BLACK, Color.CYAN,
            BitmapFactory.decodeResource(resources, R.drawable.music_4)))
        wheeldata.add(WheelData("tv5", Color.BLACK, Color.MAGENTA,
            BitmapFactory.decodeResource(resources, R.drawable.tv_5)))
        wheeldata.add(WheelData("news6", Color.BLACK, Color.YELLOW,
            BitmapFactory.decodeResource(resources, R.drawable.news_6)))

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
        binding.wheel.setRotationViaSwipe(true,1)
        binding.wheel.setTargetReachListener{ wheeldata ->
            Log.e("wheel",wheeldata.text)
            val bundle = Bundle()
            val intent = Intent(this, GetQuestionDetails::class.java)
            bundle.putString("category",wheeldata.text)
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

}
