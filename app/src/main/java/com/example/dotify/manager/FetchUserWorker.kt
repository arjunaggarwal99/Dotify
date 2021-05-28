package com.example.dotify.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dotify.DotifyApplication

class FetchUserWorker(context: Context, workerParams: WorkerParameters):
CoroutineWorker(context, workerParams) {

    private val myApp by lazy { context.applicationContext as DotifyApplication }

    override suspend fun doWork(): Result {
        Log.i("FetchUserManager", "fetching user now")

        return Result.success()
    }

}