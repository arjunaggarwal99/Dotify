package com.example.dotify.manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val FETCH_USER_TAG = "FETCH_USER_TAG"

class FetchUserManager(context: Context) {

    // Getting the work manager
    private val workManager: WorkManager = WorkManager.getInstance(context)

    // Refresh Periodically
    fun getUserPeriodically() {

        val request = PeriodicWorkRequestBuilder<FetchUserWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(FETCH_USER_TAG)
            .build()

        workManager.enqueue(request)

    }

    // Stop Refresh
    fun stopGetUserPeriodically() {
        workManager.cancelAllWorkByTag(FETCH_USER_TAG)
    }

}