package com.example.mealsearchapp.data.remote

import com.example.mealsearchapp.data.dto.MealDTO
import com.example.mealsearchapp.data.dto.MealsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {

    @GET("api/json/v1/1/search.php")
    suspend fun getMealsList(@Query("s") query: String) : MealsDTO

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealDetail(@Query("i") mealId: String) : MealsDTO
}