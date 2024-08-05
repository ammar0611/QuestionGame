package com.example.questiongame.app.getquestion.usecase

import android.util.Log
import com.example.questiongame.api.Repository
import com.example.questiongame.model.response.CatMainData
import com.example.questiongame.model.response.QueMainData
import retrofit2.Response
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getCategory1() : Response<QueMainData> {
        Log.e("Category used in API","Cat1")
        return repository.getCategory1()
    }

    suspend fun getCategory2() : Response<QueMainData> {
        Log.e("Category used in API","Cat2")
        return repository.getCategory2()
    }

    suspend fun getCategory3() : Response<QueMainData> {
        Log.e("Category used in API","Cat3")
        return repository.getCategory3()
    }

    suspend fun getCategory4() : Response<QueMainData> {
        Log.e("Category used in API","Cat4")
        return repository.getCategory4()
    }

    suspend fun getCategory5() : Response<QueMainData> {
        Log.e("Category used in API","Cat5")
        return repository.getCategory5()
    }

    suspend fun getCategory6() : Response<QueMainData> {
        Log.e("Category used in API","Cat6")
        return repository.getCategory6()
    }

}