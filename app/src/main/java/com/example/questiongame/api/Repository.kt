package com.example.questiongame.api

import android.util.Log
import com.example.questiongame.model.response.CatMainData
import com.example.questiongame.model.response.QueMainData
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService){
    
    suspend fun getAllCategory() : Response<CatMainData> {
        return apiService.getAllCategory()
    }

    suspend fun getCategory1() : Response<QueMainData> {
        return apiService.getCategory1()
    }

    suspend fun getCategory2() : Response<QueMainData> {
        return apiService.getCategory2()
    }

    suspend fun getCategory3() : Response<QueMainData> {
        return apiService.getCategory3()
    }

    suspend fun getCategory4() : Response<QueMainData> {
        return apiService.getCategory4()
    }

    suspend fun getCategory5() : Response<QueMainData> {
        return apiService.getCategory5()
    }

    suspend fun getCategory6() : Response<QueMainData> {
        return apiService.getCategory6()
    }

}