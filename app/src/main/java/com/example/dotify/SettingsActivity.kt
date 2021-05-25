package com.example.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ericchee.songdataprovider.Song
import com.example.dotify.databinding.ActivitySettingsBinding


fun navigateToSettingsActivity(context: Context, plays: Int) {
    val intent = Intent(context, SettingsActivity::class.java)
    val bundle = Bundle().apply {
        putInt(PLAYS_KEY, plays)
    }
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHost) }
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        title = "Settings"
        navController.setGraph(R.navigation.nav_graph, intent.extras)
        setupActionBarWithNavController(navController)



    }
    override fun onSupportNavigateUp() = navController.navigateUp()
}