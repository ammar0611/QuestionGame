package com.questions.game.api

sealed class Resource<T> {
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()
    data class Error<T>(val message: String?) : Resource<T>()
    data class Data<T>(val data: String?) : Resource<T>()
    data class Success<T>(val response: T) : Resource<T>()

}