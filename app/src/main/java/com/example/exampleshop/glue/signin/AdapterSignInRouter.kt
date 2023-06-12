package com.example.exampleshop.glue.signin

import com.example.exampleshop.R
import com.example.navigation.GlobalNavComponentRouter
import com.example.sign_in.presentation.SignInRouter
import com.example.sign_up.presentation.signup.SignUpFragment
import javax.inject.Inject

class AdapterSignInRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
): SignInRouter {

    override fun launchSignUp(login: String) {
        val screen = SignUpFragment.Screen(login)
        globalNavComponentRouter.launch(R.id.signInFragment, screen)
    }

    override fun launchMain() {
       globalNavComponentRouter.startTabs()
    }
}