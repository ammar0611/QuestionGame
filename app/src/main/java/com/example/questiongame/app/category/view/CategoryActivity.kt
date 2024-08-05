package com.example.questiongame.app.category.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questiongame.R
import com.example.questiongame.adapter.VerticalAdapter
import com.example.questiongame.api.Resource
import com.example.questiongame.app.category.viewmodel.CategoryViewModel
import com.example.questiongame.databinding.ActivityCategoryBinding
import com.example.questiongame.databinding.ActivityMainBinding
import com.example.questiongame.model.response.CatAllData
import com.example.questiongame.ui.SelectCatQue
import com.example.questiongame.utils.Constant.selectedItems
import com.example.questiongame.utils.hideProgress
import com.example.questiongame.utils.showProgress
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    var catResList: ArrayList<CatAllData>? = null

    var verticalAdapter: VerticalAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        setupObserver()

    }
    private fun setupObserver() {
        viewModel.getCategoryResponse.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    if (it.isLoading){
                        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                        showProgress(this,true)
                    }
                    else{ hideProgress() }
                }

                is Resource.Success -> {
                    verticalAdapter?.setData(it.response?.data!!, object : VerticalAdapter.OnItemClickListener {
                        override fun onClickItem(position: Int) {
                        }
                    })
                }

                is Resource.Error -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("Error", it.message.toString())
                }

                is Resource.Data -> {

                }
            }
        }
        viewModel.showButtonEvent.observe(this) {
            binding.btnNext.visibility = View.VISIBLE
        }
        viewModel.removeButtonEvent.observe(this) {
            binding.btnNext.visibility = View.GONE
        }
    }
    fun initView(){
        viewModel.getCategory()
        verticalAdapter = VerticalAdapter(viewModel)
        binding.verticalRecyclerview.adapter = verticalAdapter
        binding.verticalRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.btnNext.visibility= View.GONE
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this,SelectCatQue::class.java))
        }
        selectedItems.clear()
    }
}