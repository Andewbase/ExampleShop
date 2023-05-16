package com.example.exampleshop.app.screens.main.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.exampleshop.R
import com.example.exampleshop.app.model.accounts.entities.SignInData
import com.example.exampleshop.app.screens.base.BaseFragment
import com.example.exampleshop.app.utils.observeEvent
import com.example.exampleshop.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    override val viewModel by viewModels<SignInViewModel>()

    private lateinit var binding: FragmentSignInBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)

        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.signUpButton.setOnClickListener { onSignUpButtonPressed() }

        observeState()
        observeClearPasswordEvent()
        observeShowAuthErrorMessageEvent()
        observeNavigateToTabsEvent()
    }

    private fun onSignInButtonPressed() {
        val signInData = SignInData(
            login = binding.loginEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
        viewModel.signIn(signInData)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.loginTextInput.error = if (it.emptyLoginError) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if (it.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.loginTextInput.isEnabled = it.enableViews
        binding.passwordTextInput.isEnabled = it.enableViews
        binding.signInButton.isEnabled = it.enableViews
        binding.signUpButton.isEnabled = it.enableViews
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowAuthErrorMessageEvent() = viewModel.showAuthToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun observeClearPasswordEvent() = viewModel.clearPasswordEvent.observeEvent(viewLifecycleOwner) {
        binding.passwordEditText.text?.clear()
    }

    private fun observeNavigateToTabsEvent() = viewModel.navigateToTabsEvent.observeEvent(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
    }

    private fun onSignUpButtonPressed() {
        val login = binding.loginEditText.text.toString()
        val loginArgs = if (login.isBlank())
            null
        else{
            login
        }

        val directions = SignInFragmentDirections.actionSignInFragmentToSignUpFragment(loginArgs)
        findNavController().navigate(directions)
    }

}