package com.example.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.example.dotify.repository.DataRepository

class DotifyApplication: Application() {
    lateinit var dataRepository: DataRepository
    var selectedSong : Song? = null

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()
    }
}