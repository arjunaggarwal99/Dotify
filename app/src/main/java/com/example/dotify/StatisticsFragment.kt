package com.example.dotify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.dotify.databinding.FragmentProfileBinding
import com.example.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private val safeArgs: StatisticsFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Profile Statistics"
        val binding = FragmentStatisticsBinding.inflate(inflater)

        //val numOfGroups: Int = safeArgs.numOfGroups

       with(binding) {
           tvStatPlays.text = "Play count: ${safeArgs.PLAYSKEY}"
       }

        return binding.root
    }
}