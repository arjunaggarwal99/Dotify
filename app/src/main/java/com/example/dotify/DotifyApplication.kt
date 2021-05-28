package com.example.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.example.dotify.manager.FetchUserManager
import com.example.dotify.repository.DataRepository

class DotifyApplication: Application() {
    lateinit var dataRepository: DataRepository
    var selectedSong : Song? = null
    lateinit var fetchUserManager: FetchUserManager

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()
        this.fetchUserManager = FetchUserManager(this)
    }
}