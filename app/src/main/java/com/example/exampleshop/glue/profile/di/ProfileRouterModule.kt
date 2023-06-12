package com.example.exampleshop.glue.profile.di

import com.example.exampleshop.glue.profile.AdapterProfileRouter
import com.example.profile.presentation.ProfileRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface ProfileRouterModule {

    @Binds
    fun bindProfileRouter(
        profileRouter: AdapterProfileRouter
    ): ProfileRouter

}