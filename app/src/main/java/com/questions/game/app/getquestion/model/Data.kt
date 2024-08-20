package com.questions.game.app.getquestion.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("category_id")
    var categoryId: Int? = null,

    @SerializedName("age_id")
    var ageId: String? = null,

    @SerializedName("time_Allowed")
    var timeAllowed: Int? = null,

    @SerializedName("question")
    var question: String? = null,

    @SerializedName("answer1")
    var answer1: String? = null,

    @SerializedName("answer2")
    var answer2: String? = null,

    @SerializedName("answer3")
    var answer3: String? = null,

    @SerializedName("answer4")
    var answer4: String? = null,

    @SerializedName("hint")
    var hint: String? = null,

    @SerializedName("correct_Answer")
    var correctAnswer: String? = null,

    @SerializedName("image_URL")
    var imageURL: String? = null,

    @SerializedName("video_URL")
    var videoURL: String? = null,

    @SerializedName("sound_URL")
    var soundURL: String? = null,

    @SerializedName("fileType")
    var fileType: Int? = null,

    @SerializedName("pack_ID")
    var packID: Int? = null,

    @SerializedName("questionMasterId") 
    var questionMasterId: Int? = null,

    @SerializedName("status") 
    var status: Int? = null,

    @SerializedName("created") 
    var created: String? = null,

    @SerializedName("modified")
    var modified: String? = null,

    @SerializedName("creditBy") 
    var creditBy: String? = null,

    @SerializedName("questionActiveStatus")
    var questionActiveStatus: Int? = null,

    @SerializedName("priority") 
    var priority: Int? = null,

    @SerializedName("SupportVideoURL")
    var SupportVideoURL: String? = null,

    @SerializedName("AnswerOrder")
    var AnswerOrder: String? = null,

    @SerializedName("ringType")
    var ringType: Int? = null,

    @SerializedName("ascendingPriority")
    var ascendingPriority: Int? = null,

    @SerializedName("newPriority")
    var newPriority: Int? = null,

    @SerializedName("id")
    var id: Int? = null
)
