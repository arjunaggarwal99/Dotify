package com.example.dotify

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val randomNumber = Random.nextInt(1000, 10000)
    private lateinit var playButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var albumImage: ImageView
    private lateinit var changeButton: Button
    private lateinit var newId: EditText
    private lateinit var userId: TextView
    private lateinit var plays: TextView
    private var currValue = randomNumber

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        playButton = findViewById(R.id.playButton)
        albumImage = findViewById(R.id.albumImage)
        changeButton = findViewById(R.id.changeButton)
        userId = findViewById(R.id.userId)
        newId = findViewById(R.id.newId)
        plays = findViewById(R.id.plays)

        // making the new id edit text invisible when the app loads
        newId.visibility = View.INVISIBLE

        // Setting the initial plays
        plays.text = randomNumber.toString() + " plays"

        // Next Button
        nextButton.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        // Back Button
        backButton.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        // Play Button
        playButton.setOnClickListener {
            currValue = currValue + 1
            plays.text = currValue.toString() + " plays"
        }

        // Cover Image
        albumImage.setOnLongClickListener {
            plays.setTextColor(Color.rgb(200, 0, 0))
            return@setOnLongClickListener true
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
}
