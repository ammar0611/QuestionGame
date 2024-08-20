package com.questions.game.app.category.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.questions.game.api.Resource
import com.questions.game.app.category.usecase.CategoryUseCase
import com.questions.game.app.category.model.CatMainDataRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(private val useCase: CategoryUseCase):ViewModel() {

    val getCategoryResponse: MutableLiveData<Resource<CatMainDataRes?>> = MutableLiveData()
    val showButtonEvent:MutableLiveData<Unit> = MutableLiveData()
    val removeButtonEvent:MutableLiveData<Unit> = MutableLiveData()

    fun showButton(){
        viewModelScope.launch {
            showButtonEvent.value = Unit
        }
    }
    fun removeButton(){
        viewModelScope.launch {
            removeButtonEvent.value = Unit
        }
    }

    fun getCategory(){
        viewModelScope.launch {
            try {
                getCategoryResponse.value = Resource.Loading(true)
                val apiResponse = useCase.getAllCategory()
                if (apiResponse.isSuccessful){
                    getCategoryResponse.value = Resource.Loading(false)
                    getCategoryResponse.value = Resource.Success(apiResponse.body())
                } else {
                    getCategoryResponse.value = Resource.Loading(false)
                    getCategoryResponse.value = Resource.Error(apiResponse.message())
                }
            } catch (e: Exception){
                getCategoryResponse.value = Resource.Loading(false)
                getCategoryResponse.value = Resource.Error(e.message)
            }
        }
    }

}