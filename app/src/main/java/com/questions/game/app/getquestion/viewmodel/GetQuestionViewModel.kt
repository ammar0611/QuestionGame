package com.questions.game.app.getquestion.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.questions.game.api.Resource
import com.questions.game.app.getquestion.usecase.GetQuestionUseCase
import com.questions.game.app.getquestion.model.QueMainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetQuestionViewModel @Inject constructor(private val useCase: GetQuestionUseCase):ViewModel() {

    val getQuestionResponse: MutableLiveData<Resource<QueMainData?>> = MutableLiveData()
    var getCategoryNum = 0


    fun getQuestion(){
        viewModelScope.launch {
            try {
                getQuestionResponse.value = Resource.Loading(true)
                val apiResponse = when(getCategoryNum){
                    1 -> useCase.getCategory1()
                    2 -> useCase.getCategory2()
                    3 -> useCase.getCategory3()
                    4 -> useCase.getCategory4()
                    5 -> useCase.getCategory5()
                    6 -> useCase.getCategory6()
                    else -> { useCase.getCategory1() }
                }

                if (apiResponse.isSuccessful){
                    getQuestionResponse.value = Resource.Loading(false)
                    getQuestionResponse.value = Resource.Success(apiResponse.body())
                } else {
                    getQuestionResponse.value = Resource.Loading(false)
                    getQuestionResponse.value = Resource.Error(apiResponse.message())
                }
            } catch (e: Exception){
                getQuestionResponse.value = Resource.Loading(false)
                getQuestionResponse.value = Resource.Error(e.message)
            }
        }
    }

}