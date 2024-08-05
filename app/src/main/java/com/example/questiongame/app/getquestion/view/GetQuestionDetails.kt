package com.example.questiongame.app.getquestion.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.questiongame.R
import com.example.questiongame.api.Resource
import com.example.questiongame.app.getquestion.viewmodel.GetQuestionViewModel
import com.example.questiongame.databinding.ActivityGetQuestionDetailsBinding
import com.example.questiongame.model.response.Data
import com.example.questiongame.ui.PlayQuestionActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class GetQuestionDetails : AppCompatActivity() {
    lateinit var binding: ActivityGetQuestionDetailsBinding
    private val viewModel: GetQuestionViewModel by viewModels()
    private lateinit var tempDataList: ArrayList<Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetQuestionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupObserver()

    }

    private fun initView(){
        Handler().postDelayed({
            binding.btnNext.visibility = View.VISIBLE
        }, 3000)
        val bundle = intent.extras
        when (bundle?.getString("category")) {
            "quiz1" -> {
                binding.img.setImageResource(R.drawable.quiz_1)
                viewModel.getCategoryNum = 1
            }

            "school2" -> {
                binding.img.setImageResource(R.drawable.school_2)
                viewModel.getCategoryNum = 2
            }

            "quick3" -> {
                binding.img.setImageResource(R.drawable.quick_3)
                viewModel.getCategoryNum = 3
            }

            "music4" -> {
                binding.img.setImageResource(R.drawable.music_4)
                viewModel.getCategoryNum = 4
            }

            "tv5" -> {
                binding.img.setImageResource(R.drawable.tv_5)
                viewModel.getCategoryNum = 5
            }

            "news6" -> {
                binding.img.setImageResource(R.drawable.news_6)
                viewModel.getCategoryNum = 6
            }
        }
        viewModel.getQuestion()
    }

    private fun setupObserver(){
        viewModel.getQuestionResponse.observe(this){
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val askedList = it.response?.data?.askedQuestionslist
                    val list:List<String> = askedList.toString().split(",")
                    val newList:MutableList<Data> = mutableListOf()

                    for(data in it.response?.data!!.data){
                        if(data.questionMasterId.toString() !in list){
                            newList.add(data)
                        }
                    }
                    Log.d("ALLDATA Count",it.response.data!!.data.size.toString())
                    Log.d("ASKEDQ Count",list.size.toString())
                    Log.d("NEWDATA Count",newList.size.toString())
                    Log.d("NEWDATA",newList.toString())
                    val sendList = newList[Random.nextInt(newList.size)]
                    Log.e("SENDLIST",sendList.toString())
                    binding.btnNext.setOnClickListener {
                        val intent = Intent(this,PlayQuestionActivity::class.java)
                        intent.putExtras(sendQuestion(sendList))
                        startActivity(intent)
                    }
                }

                is Resource.Error -> {
                    Log.e("Error", it.message.toString())
                }

                is Resource.Data -> {

                }
            }
        }
    }

    private fun sendQuestion(list:Data):Bundle{
        val question = list.question
        val answer1 = list.answer1
        val answer2 = list.answer2
        val answer3 = list.answer3
        val answer4 = list.answer4
        val correct_answer = list.correctAnswer
        val image_url = list.imageURL
        val video_url = list.videoURL
        val arraylist = arrayOf(question,answer1,answer2,answer3,answer4,correct_answer,image_url,video_url)
        val bundle:Bundle= Bundle()
        bundle.putStringArray("LIST",arraylist)
        return bundle
    }
}