package com.example.questiongame.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.questiongame.app.category.view.CategoryActivity
import com.example.questiongame.databinding.ActivityGameDetailsBinding

class GameDetails : AppCompatActivity() {
    private lateinit var binding:ActivityGameDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toogleGametype.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    binding.btnStandard.id -> Log.e("00", "Standard")
                    binding.btnPoints.id -> Log.e("00", "Points")
                }
            }
        }

        binding.toogleAgetype.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    binding.btnKids.id -> Log.e("00", "Kids")
                    binding.btnTeen.id -> Log.e("00", "Teen")
                    binding.btnAdult.id -> Log.e("00", "Adult")
                }
            }
        }

        binding.toogleTime.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    binding.btn90min.id -> Log.e("00", "90 min")
                    binding.btn60min.id -> Log.e("00", "60 min")
                    binding.btn30min.id -> Log.e("00", "30 min")

                }
            }
        }
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this,CategoryActivity::class.java))
        }

    }
}