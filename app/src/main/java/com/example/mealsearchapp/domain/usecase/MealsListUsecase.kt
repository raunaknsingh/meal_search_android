package com.example.mealsearchapp.domain.usecase

import com.example.mealsearchapp.common.Resource
import com.example.mealsearchapp.data.dto.toDomainMeal
import com.example.mealsearchapp.domain.model.Meal
import com.example.mealsearchapp.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MealsListUsecase @Inject constructor(private val mealsRepository: MealsRepository) {

    operator fun invoke(query: String): Flow<Resource<List<Meal>>> = flow {
        try {
            emit(Resource.Loading())
            val response = mealsRepository.getMealsList(query)
            val data = response.meals?.map { it.toDomainMeal() } ?: emptyList()
            emit(Resource.Success(data = data))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Http Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Some error occured!"))
        }
    }
}