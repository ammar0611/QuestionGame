package com.example.questiongame.model.response

import com.google.gson.annotations.SerializedName


data class QueMainData(
    @SerializedName("data")
    var data : AllData? = null
)