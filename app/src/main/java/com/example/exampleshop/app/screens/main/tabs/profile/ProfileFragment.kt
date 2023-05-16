package com.example.exampleshop.app.screens.main.tabs.profile


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.exampleshop.R
import com.example.exampleshop.app.screens.base.BaseFragment
import com.example.exampleshop.app.utils.findTopNavController
import com.example.exampleshop.app.utils.observeResults
import com.example.exampleshop.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)



        binding.logoutButton.setOnClickListener { logout() }
        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.resultView.setTryAgainAction { viewModel.reload() }

        observeAccountDetails()
    }

    private fun observeAccountDetails() {
        viewModel.account.observeResults(this, binding.root, binding.resultView){ account ->
            binding.emailTextView.text = account.email
            binding.usernameTextView.text = account.username
        }
    }

    private fun onEditProfileButtonPressed() {
        findTopNavController().navigate(R.id.editProfileFragment)
    }

}