package com.example.questiongame.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.questiongame.app.getquestion.view.GetQuestionDetails
import com.example.questiongame.databinding.ActivitySelectCatQueBinding

class SelectCatQue : AppCompatActivity(),View.OnClickListener{
    private lateinit var binding:ActivitySelectCatQueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCatQueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.quiz1.setOnClickListener(this)
        binding.school2.setOnClickListener(this)
        binding.quick3.setOnClickListener(this)
        binding.music4.setOnClickListener(this)
        binding.tv5.setOnClickListener(this)
        binding.news6.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val bundle = Bundle()
        val intent = Intent(this, GetQuestionDetails::class.java)
        when(v?.id){
            binding.quiz1.id->{
                bundle.putString("category","quiz1")
                intent.putExtras(bundle)
                startActivity(intent)
            }
            binding.school2.id->{
                bundle.putString("category","school2")
                intent.putExtras(bundle)
                startActivity(intent)
            }
            binding.quick3.id->{
                bundle.putString("category","quick3")
                intent.putExtras(bundle)
                startActivity(intent)
            }
            binding.music4.id->{
                bundle.putString("category","music4")
                intent.putExtras(bundle)
                startActivity(intent)
            }
            binding.tv5.id->{
                bundle.putString("category","tv5")
                intent.putExtras(bundle)
                startActivity(intent)
            }
            binding.news6.id->{
                bundle.putString("category","news6")
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}
