package com.example.questiongame.model.response

import com.google.gson.annotations.SerializedName

data class AllData(
    @SerializedName("status")
    var status: String?= null,

    @SerializedName("data")
    var data: ArrayList<Data> = arrayListOf(),

    @SerializedName("askedQuestionslist")
    var askedQuestionslist : String? = null
)
