package com.questions.game.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caneryilmaz.apps.luckywheel.data.WheelData
import com.questions.game.R
import com.questions.game.app.getquestion.view.GetQuestionDetails
import com.questions.game.databinding.ActivityTestBinding
import com.questions.game.spinner.model.SpinWheelItemSectionModel
import com.questions.game.utils.LogUtil.e
import kotlin.random.Random


class Test : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    private var data: ArrayList<SpinWheelItemSectionModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSpinner()
    }

    private fun setSpinner() {
        val luckySpinnerView = binding.wheel

        data.add(addData("1", "Quiz",Color.parseColor("#ED7A31")))
        data.add(addData("2", "School",Color.parseColor("#9E4090")))
        data.add(addData("3", "Quick",Color.parseColor("#74B946")))
        data.add(addData("4", "Music",Color.parseColor("#2997CE")))
        data.add(addData("5", "Tv",Color.parseColor("#F3CC2D")))
        data.add(addData("6", "News",Color.parseColor("#E74F35")))


        luckySpinnerView.setData(data)
        luckySpinnerView.setRound(4)
        var target:Int = probability()
        if(target == 0){
            target = 6
        }
        luckySpinnerView.setPredeterminedNumber(5)
        binding.btnSpin.setOnClickListener {
            luckySpinnerView.startLuckyWheelWithTargetIndex(5)
        }

        //you will get the value of result in this callback.
        luckySpinnerView.setLuckyRoundItemSelectedListener { index ->
            Toast.makeText(
                applicationContext,
                "You got index $index",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun addData(topText: String, secondaryText: String, color: Int): SpinWheelItemSectionModel {
        val spinWheelItemSectionModel = SpinWheelItemSectionModel()
        spinWheelItemSectionModel.topText = topText
        spinWheelItemSectionModel.secondaryText = secondaryText
        spinWheelItemSectionModel.color = color
        return spinWheelItemSectionModel
    }
    fun probability():Int{
        var num = 0
        val list = listOf(0.1,0.2,0.1,0.5,0.3,0.1,2.0)
        val sortedList=list.sorted()
        e("sorted list",sortedList.toString())
        var cumulativeProb = 0.0
        var prevI = 0.1
        val random1 = Random.nextDouble(0.1,3.3 )
        val random = String.format("%.1f", random1).toDouble()
        e("Random num",random.toString())
        for(i in sortedList){
            cumulativeProb += i
            if(cumulativeProb >= random){
                if (cumulativeProb > random){
                    num = list.indexOf(prevI)
                    break
                }
                num = list.indexOf(i)
                break
            }
            prevI = i
        }
        e("num",num.toString())
        return num
    }
}









































//val wheelData = ArrayList<WheelData>()
//wheelData.add(
//WheelData("quiz1", Color.BLACK, Color.RED,
//BitmapFactory.decodeResource(resources, R.drawable.quiz_1))
//)
//wheelData.add(WheelData("school2", Color.BLACK, Color.GREEN,
//BitmapFactory.decodeResource(resources, R.drawable.school_2)))
//wheelData.add(WheelData("quick3", Color.BLACK, Color.BLUE,
//BitmapFactory.decodeResource(resources, R.drawable.quick_3)))
//wheelData.add(WheelData("music4", Color.BLACK, Color.CYAN,
//BitmapFactory.decodeResource(resources, R.drawable.music_4)))
//wheelData.add(WheelData("tv5", Color.BLACK, Color.MAGENTA,
//BitmapFactory.decodeResource(resources, R.drawable.tv_5)))
//wheelData.add(WheelData("news6", Color.BLACK, Color.YELLOW,
//BitmapFactory.decodeResource(resources, R.drawable.news_6)))
//
//binding.wheel.setWheelData(wheelData)
//binding.btnSpin.setOnClickListener {
//    binding.wheel.rotateWheel()
//}
//val list = listOf(0.1,0.2,0.1,0.5,0.3,0.1,2.0)
//val sortedList=list.sorted()
//e("sorted list",sortedList.toString())
//var cumulativeProb = 0.0
//var prevI = 0.0
//val random1 = Random.nextDouble(0.1,3.3 )
//val random = String.format("%.1f", random1).toDouble()
//e("Random num",random.toString())
//for(i in sortedList){
//    cumulativeProb += i
//    if(cumulativeProb >= random){
//        if (cumulativeProb > random){
//            binding.wheel.setTarget(target = list.indexOf(prevI))
//            break
//        }
//        binding.wheel.setTarget(target = list.indexOf(i))
//        break
//    }
//    prevI = i
//}
//binding.wheel.setRotationViaSwipe(true,1)
//binding.wheel.setTargetReachListener{ wheelData ->
//    e("wheel",wheelData.text)
//    val bundle = Bundle()
//    val intent = Intent(this, GetQuestionDetails::class.java)
//    bundle.putString("category",wheelData.text)
//    intent.putExtras(bundle)
//    startActivity(intent)
//}