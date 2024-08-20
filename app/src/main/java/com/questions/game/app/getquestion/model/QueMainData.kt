package com.questions.game.app.getquestion.model

import com.google.gson.annotations.SerializedName


data class QueMainData(
    @SerializedName("data")
    var data : AllData? = null
)