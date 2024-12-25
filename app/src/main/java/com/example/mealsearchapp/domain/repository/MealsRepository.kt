package com.example.mealsearchapp.domain.repository

import com.example.mealsearchapp.data.dto.MealsDTO

interface MealsRepository {

    suspend fun getMealsList(query: String): MealsDTO

    suspend fun getMealDetail(mealId: String): MealsDTO

}