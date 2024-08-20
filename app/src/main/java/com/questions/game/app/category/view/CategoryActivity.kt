package com.questions.game.app.category.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.questions.game.adapter.VerticalAdapter
import com.questions.game.api.Resource
import com.questions.game.app.category.viewmodel.CategoryViewModel
import com.questions.game.databinding.ActivityCategoryBinding
import com.questions.game.app.spinwheel.SelectCatQue
import com.questions.game.utils.Constant.selectedItems
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.hideProgress
import com.questions.game.utils.showProgress
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()

    private var verticalAdapter: VerticalAdapter? = null
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
                    else { hideProgress() }
                }

                is Resource.Success -> {
                    verticalAdapter?.setData(it.response?.data!!)
                }

                is Resource.Error -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    e("Error", it.message.toString())
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
    private fun initView(){
        viewModel.getCategory()
        verticalAdapter = VerticalAdapter(viewModel)
        binding.verticalRecyclerview.adapter = verticalAdapter
        binding.verticalRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.btnNext.visibility= View.GONE
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, SelectCatQue::class.java))
        }
        selectedItems.clear()
    }
}