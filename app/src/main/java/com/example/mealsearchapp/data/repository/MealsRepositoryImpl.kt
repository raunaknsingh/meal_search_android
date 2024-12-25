package com.example.mealsearchapp.data.repository

import com.example.mealsearchapp.data.dto.MealsDTO
import com.example.mealsearchapp.data.remote.MealsApi
import com.example.mealsearchapp.domain.repository.MealsRepository

class MealsRepositoryImpl(private val mealsApi: MealsApi) : MealsRepository{
    override suspend fun getMealsList(query: String): MealsDTO {
        return mealsApi.getMealsList(query)
    }

    override suspend fun getMealDetail(mealId: String): MealsDTO {
        return mealsApi.getMealDetail(mealId)
    }
}