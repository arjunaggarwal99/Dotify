package com.example.dotify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.databinding.ActivitySongListBinding
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlin.properties.Delegates

class SongListActivity : AppCompatActivity() {

    lateinit var currSongName: String
    lateinit var currArtistName: String
    var currImage by Delegates.notNull<Int>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        title = "All Songs"
        miniPlayer.isVisible = false

        with(binding) {

            val songs = SongDataProvider.getAllSongs()
            val adapter = SongListAdapter(songs)
            rvSongs.adapter = adapter

            // When clicking on an item
            adapter.onSongClickListener = { songName, artistName, bigImage ->
                //Toast.makeText(this@SongListActivity, "You clicked", Toast.LENGTH_SHORT).show()
                miniPlayer.isVisible = true
                tvPlaying.text = "$songName - $artistName"
                currSongName = songName
                currArtistName = artistName
                currImage = bigImage
            }

            // When clicking on Shuffle button
            btnShuffle.setOnClickListener {
                adapter.updateSongs(songs.toMutableList().shuffled())
            }

            // When clicking on the mini-player
            miniPlayer.setOnClickListener {
                //Toast.makeText(this@SongListActivity, "You clicked", Toast.LENGTH_SHORT).show()
                navigateToPlayerScreen(this@SongListActivity, currSongName, currArtistName, currImage)

            }
        }
    }
}