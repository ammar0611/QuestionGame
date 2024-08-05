package com.example.questiongame.app.category.usecase

import android.util.Log
import com.example.questiongame.api.Repository
import com.example.questiongame.model.response.CatMainData
import retrofit2.Response
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getAllCategory() : Response<CatMainData> {
        return repository.getAllCategory()
    }

}