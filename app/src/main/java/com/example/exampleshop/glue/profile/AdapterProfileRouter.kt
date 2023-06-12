package com.example.exampleshop.glue.profile

import com.example.core.AppRestarter
import com.example.exampleshop.R
import com.example.navigation.GlobalNavComponentRouter
import com.example.profile.presentation.ProfileRouter
import javax.inject.Inject

class AdapterProfileRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter
): ProfileRouter {

    override fun launchEditUsername() {
        globalNavComponentRouter.launch(R.id.editProfileFragment)
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun restartApp() {
        appRestarter.restartApp()
    }
}