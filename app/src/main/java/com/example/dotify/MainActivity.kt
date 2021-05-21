package com.example.dotify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat.apply
import com.ericchee.songdataprovider.Song
import com.example.dotify.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

const val currSongObj = "songObj"
const val PLAYS_KEY = "PLAYS_KEY"

fun navigateToPlayerScreen(context: Context, song: Song) {
    val intent = Intent(context, MainActivity::class.java)

    val bundle = Bundle().apply{
        putParcelable(currSongObj, song)
    }
    intent.putExtras(bundle)
    context.startActivity(intent)

}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currValue: Int = Random.nextInt(1000, 10000)
    private lateinit var playButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var settingsButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var albumImage: ImageView
    private lateinit var changeButton: Button
    private lateinit var newId: EditText
    private lateinit var userId: TextView
    private lateinit var plays: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                currValue = getInt(PLAYS_KEY, -1)
                plays.text = currValue.toString() + " plays"
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        nextButton = findViewById(R.id.nextButton)
        settingsButton = findViewById(R.id.settingsButton)
        backButton = findViewById(R.id.backButton)
        playButton = findViewById(R.id.playButton)
        albumImage = findViewById(R.id.albumImage)
        changeButton = findViewById(R.id.changeButton)
        userId = findViewById(R.id.userId)
        newId = findViewById(R.id.newId)

        // making the new id edit text invisible when the app loads
        newId.visibility = View.INVISIBLE

        // Update screen according to current song
        with(binding) {
            plays.text = currValue.toString() + " plays"
            val song: Song? = intent.getParcelableExtra<Song>(currSongObj)
            val imgResource: Int? = intent.extras?.getInt("image_id")
            songName.text = song?.title
            artistName.text = song?.artist
            if (song != null) {
                albumImage.setImageResource(song.largeImageID)
            }

            // Play Button
            playButton.setOnClickListener {
                currValue = currValue + 1
                plays.text = currValue.toString() + " plays"
            }

            // Settings Button
            settingsButton.setOnClickListener {
                navigateToSettingsActivity(this@MainActivity, song, currValue)
            }

            // Cover Image
            albumImage.setOnLongClickListener {
                plays.setTextColor(Color.rgb(200, 0, 0))
                return@setOnLongClickListener true
            }
        }

        // Next Button
        nextButton.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        // Back Button
        backButton.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        // Hiding username
        changeButton.setOnClickListener {
            userId.visibility = View.INVISIBLE
            newId.visibility = View.VISIBLE
            changeButton.setText("Apply")
            changeButton.setOnClickListener {
                val newUsername = newId.text
                userId.text = newUsername
                if (userId.text.isNullOrEmpty()) {
                    Toast.makeText(this, "Please enter a valid username!", Toast.LENGTH_LONG).show()
                }
                else {
                    userId.visibility = View.VISIBLE
                    newId.visibility = View.INVISIBLE
                }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PLAYS_KEY, currValue)
        super.onSaveInstanceState(outState)
    }
}
