package com.example.questiongame.model.response

data class CatAllData(
    var status: String? = null,
    var data: List<CatData>? = null,
    var tags: List<CatTags>? = null
)
