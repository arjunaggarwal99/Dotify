package com.example.dotify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.dotify.databinding.FragmentProfileBinding
import com.example.dotify.manager.FetchUserManager
import com.example.dotify.repository.DataRepository
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private val myApp: DotifyApplication by lazy { requireActivity().application as DotifyApplication }
    private val dataRepository: DataRepository by lazy { myApp.dataRepository }
    private lateinit var binding: FragmentProfileBinding
    lateinit var fetchUserManager: FetchUserManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"
        binding = FragmentProfileBinding.inflate(inflater)

        with(binding) {
            //making all text views invisible till we get the data
            tvName.visibility = View.INVISIBLE
            tvUsername.visibility = View.INVISIBLE
            tvPlatform.visibility = View.INVISIBLE
            tvHasNose.visibility = View.INVISIBLE
            ivProfile.visibility = View.INVISIBLE
            fetchButton.setOnCheckedChangeListener{ _, isChecked ->
                if (isChecked) {
                    fetchUserManager.getUserPeriodically()
                } else {
                    fetchUserManager.stopGetUserPeriodically()
                }
            }
        }

        loadUserData()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserData() {
        lifecycleScope.launch{
            kotlin.runCatching {
                binding.tvLoadingError.text = "Loading User Profile data..."
                binding.tvLoadingError.visibility = View.VISIBLE
                val userInfo = dataRepository.getUser()
                binding.tvLoadingError.visibility = View.INVISIBLE
                with(binding) {

                    tvName.text = "${userInfo.firstName} ${userInfo.lastName}"
                    tvName.visibility = View.VISIBLE

                    tvUsername.text = "Username: ${userInfo.username}"
                    tvUsername.visibility = View.VISIBLE

                    tvPlatform.text = "Platform: ${userInfo.platform}"
                    tvPlatform.visibility = View.VISIBLE

                    tvHasNose.text = "Do I have a nose?: ${userInfo.hasNose}"
                    tvHasNose.visibility = View.VISIBLE

                    ivProfile.load(userInfo.profilePicURL)
                    ivProfile.visibility = View.VISIBLE
                }

            }.onFailure {
                binding.ivError.visibility = View.VISIBLE
                binding.tvLoadingError.text = "Error occurred when fetching User Profile data!"
                binding.tvLoadingError.visibility = View.VISIBLE
            }
        }
    }
}