package com.questions.game.api

import com.questions.game.app.category.model.CatMainDataRes
import com.questions.game.app.getquestion.model.QueMainData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
//    Get All Category List
    @GET(ApiManager.ALL_CATEGORY)
    suspend fun getAllCategory(): Response<CatMainDataRes>

//    Get Questions of Category 1
    @GET(ApiManager.CATEGORY_1)
    suspend fun getCategory1(): Response<QueMainData>

//    Get Questions of Category 2
    @GET(ApiManager.CATEGORY_2)
    suspend fun getCategory2(): Response<QueMainData>

//    Get Questions of Category 3
    @GET(ApiManager.CATEGORY_3)
    suspend fun getCategory3(): Response<QueMainData>

//    Get Questions of Category 4
    @GET(ApiManager.CATEGORY_4)
    suspend fun getCategory4(): Response<QueMainData>

//    Get Questions of Category 5
    @GET(ApiManager.CATEGORY_5)
    suspend fun getCategory5(): Response<QueMainData>

//    Get Questions of Category 6
    @GET(ApiManager.CATEGORY_6)
    suspend fun getCategory6(): Response<QueMainData>


}