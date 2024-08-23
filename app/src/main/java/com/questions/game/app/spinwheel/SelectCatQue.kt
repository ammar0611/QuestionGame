package com.questions.game.app.spinwheel

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.questions.game.R
import com.questions.game.app.category.view.CategoryActivity
import com.questions.game.app.getquestion.view.GetQuestionDetails
import com.questions.game.databinding.ActivitySelectCatQueBinding
import com.questions.game.spinner.model.SpinWheelItemSectionModel
import com.questions.game.utils.LogUtil.e
import kotlin.random.Random

class SelectCatQue : AppCompatActivity() {
    private lateinit var binding: ActivitySelectCatQueBinding
    private var data: ArrayList<SpinWheelItemSectionModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySelectCatQueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSpinner()
    }

    private fun setSpinner() {
        val luckySpinnerView = binding.wheel

        data.add(addData("1", "Quiz", Color.parseColor("#ED7A31")))
        data.add(addData("2", "School", Color.parseColor("#9E4090")))
        data.add(addData("3", "Quick", Color.parseColor("#74B946")))
        data.add(addData("4", "Music", Color.parseColor("#2997CE")))
        data.add(addData("5", "Tv", Color.parseColor("#F3CC2D")))
        data.add(addData("6", "News", Color.parseColor("#E74F35")))


        luckySpinnerView.setData(data)
        luckySpinnerView.setRound(4)
        var target: Int = probability()
        if (target == 0) {
            target = 6
        }
        luckySpinnerView.setPredeterminedNumber(target - 1)
        binding.btnSpin.setOnClickListener {
            luckySpinnerView.startLuckyWheelWithTargetIndex(target - 1)
        }

        //you will get the value of result in this callback.
        luckySpinnerView.setLuckyRoundItemSelectedListener { index ->
            val bundle = Bundle()
            val intent = Intent(this, GetQuestionDetails::class.java)
            bundle.putString("category",getCategoryId(index).toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun getCategoryId(index: Int):Int{
       return when(index){
            5 ->  1
            0 ->  2
            1 ->  3
            2 ->  4
            3 ->  5
            4 ->  6
           else ->  1
       }
    }

    private fun addData(topText: String, secondaryText: String, color: Int): SpinWheelItemSectionModel {
        val spinWheelItemSectionModel = SpinWheelItemSectionModel()
        spinWheelItemSectionModel.topText = topText
        spinWheelItemSectionModel.secondaryText = secondaryText
        spinWheelItemSectionModel.color = color
        return spinWheelItemSectionModel
    }

    private fun probability(): Int {
        var num = 0
        val list = listOf(0.1, 0.2, 0.1, 0.5, 0.3, 0.1, 2.0)
        val sortedList = list.sorted()
        e("sorted list", sortedList.toString())
        var cumulativeProb = 0.0
        var prevI = 0.1
        
        val random1 = Random.nextDouble(0.1, 3.3)
        val random = String.format("%.1f", random1).toDouble()
        e("Random num", random.toString())
        for (i in sortedList) {
            cumulativeProb += i
            if (cumulativeProb >= random) {
                if (cumulativeProb > random) {
                    num = list.indexOf(prevI)
                    break
                }
                num = list.indexOf(i)
                break
            }
            prevI = i
        }
        e("num", num.toString())
        return num
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,CategoryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}
