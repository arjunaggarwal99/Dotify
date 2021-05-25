package com.example.dotify.repository

import com.example.dotify.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class DataRepository {
    private val songService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SongService::class.java)

    suspend fun getUser(): User = songService.getUser()
}

interface SongService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getUser(): User
}