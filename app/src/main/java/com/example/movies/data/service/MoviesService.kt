package com.example.movies.data.service

import com.example.movies.BuildConfig.BASE_URL
import com.example.movies.data.api.MoviesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieFlixApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createApiInstance(): MoviesApi = retrofit.create(MoviesApi::class.java)
}
