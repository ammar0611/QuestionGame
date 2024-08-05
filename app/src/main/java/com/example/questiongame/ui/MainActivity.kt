package com.example.questiongame.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.questiongame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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