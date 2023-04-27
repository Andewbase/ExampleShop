package com.example.exampleshop.app.screens.main.tabs.profile


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.exampleshop.app.model.Empty
import com.example.exampleshop.app.model.Error
import com.example.exampleshop.app.model.Pending
import com.example.exampleshop.app.model.Success
import com.example.exampleshop.app.screens.base.BaseFragment
import com.example.exampleshop.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAccountDetails()

        binding.logoutButton.setOnClickListener { logout() }
    }

    private fun observeAccountDetails() {
        viewModel.account.observe(viewLifecycleOwner){result ->
            when(result){
                is Success -> {
                    binding.emailTextView.text = result.value.email
                    binding.usernameTextView.text = result.value.username
                }
                is Pending -> binding.profileProgressBar.visibility = View.VISIBLE
                is Empty -> Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_SHORT).show()
                is Error -> Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

}