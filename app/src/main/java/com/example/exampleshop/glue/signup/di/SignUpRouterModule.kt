package com.example.exampleshop.glue.signup.di

import com.example.exampleshop.glue.signup.AdapterSignUpRouter
import com.example.sign_up.presentation.SignUpRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignUpRouterModule {

    @Binds
    fun bindSignUpRouter(router: AdapterSignUpRouter): SignUpRouter

}