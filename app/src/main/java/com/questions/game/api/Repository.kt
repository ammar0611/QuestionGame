package com.questions.game.api

import com.questions.game.app.category.model.CatMainDataRes
import com.questions.game.app.getquestion.model.QueMainData
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService){
    
    suspend fun getAllCategory() : Response<CatMainDataRes> {
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