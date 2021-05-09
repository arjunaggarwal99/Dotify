package com.example.dotify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.databinding.ActivitySongListBinding
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlin.properties.Delegates

class SongListActivity : AppCompatActivity() {

    lateinit var currSongName: String
    lateinit var currArtistName: String
    var currImage by Delegates.notNull<Int>()
    private var currSongObj: Song? = null

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
            adapter.onSongClickListener = { song: Song ->
                //Toast.makeText(this@SongListActivity, "You clicked", Toast.LENGTH_SHORT).show()
                miniPlayer.isVisible = true
                tvPlaying.text = "${song.title} - ${song.artist}"
                currSongName = song.title
                currArtistName = song.artist
                currImage = song.largeImageID
                currSongObj = song
            }

            // When clicking on Shuffle button
            btnShuffle.setOnClickListener {
                adapter.updateSongs(songs.toMutableList().shuffled())
            }

            // When clicking on the mini-player
            miniPlayer.setOnClickListener {
                //Toast.makeText(this@SongListActivity, "You clicked", Toast.LENGTH_SHORT).show()
                navigateToPlayerScreen(this@SongListActivity, currSongObj!!)

            }
        }
    }
}