package com.example.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.DotifyApplication
import com.example.dotify.MainActivity
import com.example.dotify.R
import java.lang.Exception
import kotlin.random.Random

const val NEW_USER_CHANNEL_ID = "NEW_USER_CHANNEL_ID"

class FetchUserWorker(
    private val context: Context, workerParams: WorkerParameters):
CoroutineWorker(context, workerParams) {

    private val myApp by lazy { context.applicationContext as DotifyApplication }
    private val dataRepository by lazy { myApp.dataRepository }
    private val fetchUserManager by lazy { myApp.fetchUserManager }
    private val notificationManager = NotificationManagerCompat.from(context)

    override suspend fun doWork(): Result {

        Log.i("FetchUserManager", "fetching user now")
        return try {

            // Making HTTP Request to fetch data
            val user = dataRepository.getUser()
            fetchUserManager.updateUser(user)
            Log.i("HTTP", "Fetching user using HTTP Request")

            // Notification after fetching data
            // Getting a random song from the Song Data Provider
            val randomSong: Song = SongDataProvider.getAllSongs().random()

            // Initialize all channels
            initNotificationChannels()

            // Publish Notification
            publishNotification(randomSong)

            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    private fun initNotificationChannels() {
        initNewSongsChannel()
        //initPromotionChannel()
    }

//    private fun initPromotionChannel() {
//
//    }

    private fun initNewSongsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.new_uploaded_music)
            val descriptionText = context.getString(R.string.new_song_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create channel object
            val channel = NotificationChannel(NEW_USER_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun publishNotification(randomSong: Song) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // Building notification information
        val builder = NotificationCompat.Builder(context, NEW_USER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_song)
            .setContentTitle("${randomSong.artist} just released a new song!!!")
            .setContentText("Listen to ${randomSong.title} now on Dotify")
            .setContentIntent(pendingIntent) // Sets the action when user clicks on the notification
            .setAutoCancel(true)    // Dismisses the Notification on tap
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Default priority is Medium

        // Publish the notification using the information
        with(notificationManager) {
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

}