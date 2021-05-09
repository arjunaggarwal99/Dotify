package com.example.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.dotify.databinding.FragmentAboutBinding
import com.example.dotify.databinding.FragmentProfileBinding

class AboutFragment : Fragment() {

    //private val safeArgs: GroupsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "About"
        val binding = FragmentAboutBinding.inflate(inflater)

        //val numOfGroups: Int = safeArgs.numOfGroups

//        with(binding) {
//            tvGroupsMsg.text = "${safeArgs.numOfGroups} - my favorite group is: ${safeArgs.favoriteGroup}"
//        }

        return binding.root
    }
}