package com.questions.game.app.getquestion.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.questions.game.R
import com.questions.game.api.Resource
import com.questions.game.app.getquestion.viewmodel.GetQuestionViewModel
import com.questions.game.databinding.ActivityGetQuestionDetailsBinding
import com.questions.game.app.getquestion.model.Data
import com.questions.game.app.playquestion.PlayQuestionActivity
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.Pref
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class GetQuestionDetails : AppCompatActivity() {
    lateinit var binding: ActivityGetQuestionDetailsBinding
    private val viewModel: GetQuestionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetQuestionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupObserver()

    }

    private fun initView(){
        val bundle = intent.extras
        when (bundle?.getString("category")) {
//            Here Index of spin wheel is get and set to viewModel
            "1" -> {
                binding.img.setImageResource(R.drawable.quiz_1)
                viewModel.getCategoryNum = 1
            }

            "2" -> {
                binding.img.setImageResource(R.drawable.school_2)
                viewModel.getCategoryNum = 2
            }

            "3" -> {
                binding.img.setImageResource(R.drawable.quick_3)
                viewModel.getCategoryNum = 3
            }

            "4" -> {
                binding.img.setImageResource(R.drawable.music_4)
                viewModel.getCategoryNum = 4
            }

            "5" -> {
                binding.img.setImageResource(R.drawable.tv_5)
                viewModel.getCategoryNum = 5
            }

            "6" -> {
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
                    val userAgeId = Pref.getValue("ageId")
                    e("USER AGE ID",userAgeId)

                    for(data in it.response?.data!!.data){
                        val ageList:List<String> = data.ageId.toString().split(",")
                        if(data.questionMasterId.toString() !in list){
                            if (data.questionMasterId.toString() !in Pref.getList("askedQuestion")) {
                                if (userAgeId in ageList) {
                                    newList.add(data)
                                }
                            }
                        }
                    }
                    Log.w("ALL DATA Count",it.response.data!!.data.size.toString())
                    Log.w("ASKED Count",list.size.toString())
                    Log.w("NEW DATA Count",newList.size.toString())
                    Log.w("NEW DATA",newList.toString())
                    val sendList = newList[Random.nextInt(newList.size)]
                    e("SEND LIST",sendList.toString())

                    if (sendList.question!!.isNotEmpty()){
                        val intent = Intent(this, PlayQuestionActivity::class.java)
                        intent.putExtras(sendQuestion(sendList))
                        startActivity(intent)
                    }
                }

                is Resource.Error -> {
                    e("Error", it.message.toString())
                }

                is Resource.Data -> {

                }
            }
        }
    }

    private fun sendQuestion(list: Data):Bundle{
        val question = list.question
        val answer1 = list.answer1
        val answer2 = list.answer2
        val answer3 = list.answer3
        val answer4 = list.answer4
        val correctAnswer = list.correctAnswer
        val imageUrl = list.imageURL
        val videoUrl = list.videoURL
        val questionId = list.questionMasterId.toString()
        val categoryId = list.categoryId.toString()
        val arraylist = arrayOf(question,answer1,answer2,answer3,answer4,correctAnswer,imageUrl,videoUrl,questionId,categoryId)
        val bundle = Bundle()
        bundle.putStringArray("LIST",arraylist)
        return bundle
    }
}