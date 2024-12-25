package com.example.mealsearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsearchapp.common.Resource
import com.example.mealsearchapp.domain.model.MealDetails
import com.example.mealsearchapp.domain.usecase.MealDetailsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsDetailViewModel @Inject constructor(private val mealDetailsUsecase: MealDetailsUsecase) :
    ViewModel() {

    private var _mealDetail = MutableSharedFlow<List<MealDetails>>()
    val mealDetail: SharedFlow<List<MealDetails>> = _mealDetail

    private var _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getMealDetail(mealId: String) {
        viewModelScope.launch {
            mealDetailsUsecase(mealId).collect {
                when (it) {
                    is Resource.Error -> {
                        _isLoading.value = false
                        _error.emit(it.message ?: "")
                    }

                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Success -> {
                        _isLoading.value = false
                        _mealDetail.emit(it.data ?: emptyList())
                    }
                }
            }
        }
    }
}