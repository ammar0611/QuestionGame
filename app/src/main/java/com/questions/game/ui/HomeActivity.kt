package com.questions.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.questions.game.app.gamedetails.GameDetails
import com.questions.game.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlaynewgame.setOnClickListener {
            startActivity(Intent(this, GameDetails::class.java))
        }
        binding.btnResumegame.setOnClickListener {
            startActivity(Intent(this, Test::class.java))
        }

    }
}