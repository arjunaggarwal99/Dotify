package com.example.dotify.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dotify.DotifyApplication
import java.lang.Exception

class FetchUserWorker(
    private val context: Context, workerParams: WorkerParameters):
CoroutineWorker(context, workerParams) {

    private val myApp by lazy { context.applicationContext as DotifyApplication }
    private val dataRepository by lazy { myApp.dataRepository }
    private val fetchUserManager by lazy { myApp.fetchUserManager }

    override suspend fun doWork(): Result {
        Log.i("FetchUserManager", "fetching user now")
        return try {
            val user = dataRepository.getUser()
            fetchUserManager.updateUser(user)
            Log.i("HTTP", "Fetching user using HTTP Request")
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

}