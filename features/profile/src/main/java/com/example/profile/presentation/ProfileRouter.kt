package com.example.profile.presentation

interface ProfileRouter {

    fun launchEditUsername()

    /**
     * Go back to the previous screen.
     */
    fun goBack()

    /**
     * Close all screens and launch the initial screen.
     */
    fun restartApp()

}