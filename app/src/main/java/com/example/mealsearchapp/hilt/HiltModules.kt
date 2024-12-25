package com.example.mealsearchapp.hilt

import com.example.mealsearchapp.common.Constants
import com.example.mealsearchapp.data.remote.MealsApi
import com.example.mealsearchapp.data.repository.MealsRepositoryImpl
import com.example.mealsearchapp.domain.repository.MealsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    @Provides
    @Singleton
    fun provideMealSearchApi(): MealsApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MealsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMealSearchRepository(mealsApi: MealsApi) : MealsRepository {
        return MealsRepositoryImpl(mealsApi)
    }
}