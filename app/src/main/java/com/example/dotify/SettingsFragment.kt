package com.example.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dotify.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private val safeArgs: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        navController.navigate(
            SettingsFragmentDirections.actionSettingsFragmentToStatisticsFragment(plays = safeArgs.plays)
        )

        with(binding) {

            profileButton.setOnClickListener{
                navController.navigate(R.id.profileFragment)
            }

            aboutButton.setOnClickListener {
                navController.navigate(R.id.aboutFragment)
            }

            statsButton.setOnClickListener {
                navController.navigate(R.id.statisticsFragment)
            }
        }


        return binding.root
    }
}