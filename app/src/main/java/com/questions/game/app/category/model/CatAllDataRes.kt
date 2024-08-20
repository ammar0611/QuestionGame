package com.questions.game.app.category.model

data class CatAllDataRes(
    var status: String? = null,
    var data: List<CatDataRes>? = null,
    var tags: List<CatTagsRes>? = null
)
