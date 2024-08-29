package com.questions.game.app.gamedetails

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.questions.game.app.category.view.CategoryActivity
import com.questions.game.databinding.ActivityGameDetailsBinding
import com.questions.game.utils.Constant
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.Pref

class GameDetails : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Pref.initialize(this)

        binding.toogleGametype.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.btnStandard.id -> e("00", "Standard")
                    binding.btnPoints.id -> {
                        Pref.setBoolean(Constant.isPointsPref, true)
                        e("00", "Points")
                    }
                }
            }
        }

        binding.toogleAgetype.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.btnKids.id -> {
                        Pref.setValue(Constant.ageIdPref, "1")
                        e("00", "Kids")
                    }

                    binding.btnTeen.id -> {
                        Pref.setValue(Constant.ageIdPref, "2")
                        e("00", "Teen")
                    }

                    binding.btnAdult.id -> {
                        Pref.setValue(Constant.ageIdPref, "3")
                        e("00", "Adult")
                    }
                }
            }
        }

        binding.toogleTime.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.btn90min.id -> e("00", "90 min")
                    binding.btn60min.id -> e("00", "60 min")
                    binding.btn30min.id -> e("00", "30 min")

                }
            }
        }
        binding.btnNext.setOnClickListener {
            if (binding.toogleAgetype.checkedButtonId == -1) {
                Toast.makeText(this, "Please Select Age", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CategoryActivity::class.java))
            }
        }
    }
}