package com.example.sign_in.presentation.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.presentation.viewBinding
import com.example.presentation.views.observe
import com.example.sign_in.R
import com.example.sign_in.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupListeners()
            observeScreenState()
        }
    }

    private fun FragmentSignInBinding.setupListeners() {
        root.setTryAgainListener { viewModel.load() }
        signInButton.setOnClickListener {
            viewModel.signIn(loginEditText.text.toString(), passwordEditText.text.toString())
        }
        signUpButton.setOnClickListener {
            viewModel.launchSignUp(loginEditText.text.toString())
        }
        loginEditText.addTextChangedListener {
            viewModel.clearLoginError()
        }
        passwordEditText.addTextChangedListener {
            viewModel.clearPasswordError()
        }
    }

    private fun FragmentSignInBinding.observeScreenState() {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            signInButton.isEnabled = state.enableButtons
            signUpButton.isEnabled = state.enableButtons
            progressBar.isInvisible = !state.showProgressBar
            loginTextInput.error = state.loginError
            passwordTextInput.error = state.passwordError
        }
    }

}