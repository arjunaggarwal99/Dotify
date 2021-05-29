package com.example.dotify.manager

import android.content.Context
import androidx.work.*
import com.example.dotify.model.User
import java.util.concurrent.TimeUnit

private const val FETCH_USER_TAG = "FETCH_USER_TAG"

class FetchUserManager(context: Context) {

    private var user: User? = null

    // Getting the work manager
    private val workManager: WorkManager = WorkManager.getInstance(context)

    // Fetch user just once
    // Just for testing purpose
    fun getUser() {
        val request = OneTimeWorkRequestBuilder<FetchUserWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(FETCH_USER_TAG)
            .build()

        workManager.enqueue(request)
    }

    // Refresh Periodically
    fun getUserPeriodically() {

        // preventing double running
        if(isFetchingUser()) {
            return
        }

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

    // Separate alternative work request that runs every 2 days when
    // the device’s battery is not too low and is connected to a network
    fun getUserPeriodicLong() {

        // preventing double running
        if(isFetchingUser()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<FetchUserWorker>(2, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
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

    // Checking if sync is running
    private fun isFetchingUser(): Boolean {
        return workManager.getWorkInfosByTag(FETCH_USER_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }

    fun updateUser(user: User) {
        this.user = user
    }

}