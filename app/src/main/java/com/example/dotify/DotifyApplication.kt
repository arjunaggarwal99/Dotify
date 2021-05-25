package com.example.dotify

import android.app.Application
import com.example.dotify.repository.DataRepository

class DotifyApplication: Application() {
    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()
    }
}