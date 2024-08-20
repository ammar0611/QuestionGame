package com.questions.game.app.category.usecase

import com.questions.game.api.Repository
import com.questions.game.app.category.model.CatMainDataRes
import retrofit2.Response
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getAllCategory() : Response<CatMainDataRes> {
        return repository.getAllCategory()
    }

}